package com.cusufcan.teketek.ui.presentation.debate

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.navArgs
import com.cusufcan.teketek.data.model.DebateRequest
import com.cusufcan.teketek.databinding.FragmentDebateBinding
import com.cusufcan.teketek.ui.adapter.chat.ChatAdapter
import com.cusufcan.teketek.ui.viewmodel.DebateViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class DebateFragment : Fragment() {
    private var _binding: FragmentDebateBinding? = null
    private val binding get() = _binding!!

    private val viewModel: DebateViewModel by viewModels()
    private lateinit var chatAdapter: ChatAdapter

    private val args: DebateFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        _binding = FragmentDebateBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        chatAdapter = ChatAdapter()

        val recyclerView = binding.chatRecyclerView
        recyclerView.adapter = chatAdapter

        val topic = args.topic
        binding.topicText.text = topic.title

        val sendButton = binding.sendButton
        val messageEditText = binding.messageEditText

        sendButton.setOnClickListener {
            val userMessage = messageEditText.text.toString().trim()
            if (userMessage.isNotEmpty()) {
                viewModel.sendUserMessage(DebateRequest(userMessage))

                // Clean the input field
                messageEditText.text.clear()

                // Clear the focus from the input field
                messageEditText.clearFocus()

                // Hide the keyboard
                val imm = requireContext().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                imm.hideSoftInputFromWindow(view.windowToken, 0)
            }
        }

        messageEditText.setOnEditorActionListener { _, _, _ ->
            sendButton.performClick()
            messageEditText.clearFocus()
            false
        }

        lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.messages.collectLatest { messageList ->
                    if (messageList.isEmpty()) return@collectLatest
                    chatAdapter.submitMessage(messageList.last())
                    recyclerView.scrollToPosition(chatAdapter.itemCount - 1)
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}