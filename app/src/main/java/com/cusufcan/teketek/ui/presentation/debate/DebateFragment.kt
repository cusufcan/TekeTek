package com.cusufcan.teketek.ui.presentation.debate

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.DecelerateInterpolator
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.ImageButton
import android.widget.TextView
import androidx.activity.OnBackPressedCallback
import androidx.activity.addCallback
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.RecyclerView
import com.airbnb.lottie.LottieAnimationView
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

    private lateinit var textEmpty: TextView
    private lateinit var topicText: TextView
    private lateinit var recyclerView: RecyclerView
    private lateinit var sendButton: ImageButton
    private lateinit var closeButton: ImageButton
    private lateinit var messageEditText: EditText
    private lateinit var typingAnimation: LottieAnimationView

    private lateinit var onBackPressedCallback: OnBackPressedCallback

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
        textEmpty = binding.textEmpty
        recyclerView = binding.chatRecyclerView
        topicText = binding.topicText
        sendButton = binding.sendButton
        closeButton = binding.closeButton
        messageEditText = binding.messageEditText
        typingAnimation = binding.typingAnimation
    }

    private fun bindAdapter() {
        chatAdapter = ChatAdapter(lifecycleScope, recyclerView)
        recyclerView.adapter = chatAdapter
    }

    private fun bindEvents() {
        bindBackPressed()

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

        closeButton.setOnClickListener {
            onBackPressedCallback.handleOnBackPressed()
        }
    }

    private fun bindBackPressed() {
        onBackPressedCallback = requireActivity().onBackPressedDispatcher.addCallback(this) {
            // TODO: Navigate to home screen
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
                    binding.textEmpty.visibility = View.GONE
                    chatAdapter.submitMessage(messageList.last())
                    recyclerView.smoothScrollToPosition(chatAdapter.itemCount - 1)
                }
            }
        }

        lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.isLoading.collectLatest { isLoading ->
                    if (isLoading) showTypingIndicator() else hideTypingIndicator()
                }
            }
        }
    }

    private fun showTypingIndicator() {
        typingAnimation.visibility = View.VISIBLE
        typingAnimation.scaleX = 0f
        typingAnimation.scaleY = 0f
        typingAnimation.alpha = 0f

        typingAnimation.animate().scaleX(1f).scaleY(1f).alpha(1f).setDuration(400)
            .setInterpolator(DecelerateInterpolator()).start()

        typingAnimation.playAnimation()
    }

    private fun hideTypingIndicator() {
        typingAnimation.animate().scaleX(0f).scaleY(0f).alpha(0f).setDuration(300).withEndAction {
            typingAnimation.cancelAnimation()
            typingAnimation.visibility = View.GONE
        }.start()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}