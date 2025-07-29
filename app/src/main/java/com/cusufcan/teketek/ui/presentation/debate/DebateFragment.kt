package com.cusufcan.teketek.ui.presentation.debate

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.ImageButton
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.RecyclerView
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

    private lateinit var topicText: TextView
    private lateinit var recyclerView: RecyclerView
    private lateinit var sendButton: ImageButton
    private lateinit var messageEditText: EditText

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        _binding = FragmentDebateBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bindViews()
        bindEvents()
        bindAdapter()
        bindArgs()
        observeData()
    }

    private fun bindViews() {
        recyclerView = binding.chatRecyclerView
        topicText = binding.topicText
        sendButton = binding.sendButton
        messageEditText = binding.messageEditText
    }

    private fun bindAdapter() {
        chatAdapter = ChatAdapter()
        recyclerView.adapter = chatAdapter
    }

    private fun bindEvents() {
        sendButton.setOnClickListener {
            val userMessage = messageEditText.text.toString().trim()
            if (userMessage.isNotEmpty()) {
                viewModel.sendUserMessage(DebateRequest(userMessage))

                messageEditText.text.clear()
                messageEditText.clearFocus()

                val imm =
                    requireContext().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                imm.hideSoftInputFromWindow(view?.windowToken, 0)
            }
        }

        messageEditText.setOnEditorActionListener { _, _, _ ->
            sendButton.performClick()
            messageEditText.clearFocus()
            false
        }
    }

    private fun bindArgs() {
        val topic = args.topic
        topicText.text = topic.title
    }

    private fun observeData() {
        lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.messages.collectLatest { messageList ->
                    if (messageList.isEmpty()) return@collectLatest
                    chatAdapter.submitMessage(messageList.last())
                    recyclerView.smoothScrollToPosition(chatAdapter.itemCount - 1)
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}