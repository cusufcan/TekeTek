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
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.RecyclerView
import com.airbnb.lottie.LottieAnimationView
import com.cusufcan.teketek.R
import com.cusufcan.teketek.databinding.FragmentDebateBinding
import com.cusufcan.teketek.domain.model.Message
import com.cusufcan.teketek.domain.model.Summary
import com.cusufcan.teketek.ui.adapter.chat.ChatAdapter
import com.cusufcan.teketek.ui.event.DebateUiEvent
import com.cusufcan.teketek.ui.viewmodel.DebateViewModel
import com.cusufcan.teketek.util.AppLogger
import com.cusufcan.teketek.util.Resource
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
        startDebate()
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
        chatAdapter = ChatAdapter(lifecycleScope, recyclerView) {
            sendButton.isEnabled = true
            messageEditText.isEnabled = true
            val isFinished = viewModel.eventFlow.value is DebateUiEvent.FinishDebate
            if (isFinished) {
                finishDebate()
            }
        }
        recyclerView.adapter = chatAdapter
    }

    private fun bindEvents() {
        bindBackPressed()

        sendButton.setOnClickListener {
            if (viewModel.eventFlow.value is DebateUiEvent.FinishDebate) {
                val uiEvent = viewModel.eventFlow.value as DebateUiEvent.FinishDebate
                val summary = Summary(
                    debateId = uiEvent.debateId,
                    summary = uiEvent.summary,
                    strengths = uiEvent.strengths,
                    weaknesses = uiEvent.weaknesses,
                    message = uiEvent.message,
                )
                val action = DebateFragmentDirections.actionDebateFragmentToSummaryFragment(
                    topic = args.topic,
                    summary = summary,
                )
                findNavController().navigate(action)
                return@setOnClickListener
            }

            val userMessage = messageEditText.text.toString().trim()
            if (userMessage.isNotEmpty()) {
                viewModel.next(userMessage)

                messageEditText.text.clear()
                clearFocus()
            }
        }

        closeButton.setOnClickListener {
            onBackPressedCallback.handleOnBackPressed()
        }
    }

    private fun bindBackPressed() {
        onBackPressedCallback = requireActivity().onBackPressedDispatcher.addCallback(this) {
            val title = getString(R.string.end_debate_title)
            val desc = getString(R.string.end_debate_desc)
            val action = DebateFragmentDirections.actionDebateFragmentToAlertDialogFragment(
                title,
                desc,
            )
            findNavController().navigate(action)
        }
    }

    private fun startDebate() {
        val topic = args.topic
        topicText.text = topic.title
        viewModel.start(topic.title)
    }

    private fun observeData() {
        lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiState.collectLatest { resource ->
                    when (resource) {
                        is Resource.Loading -> {
                            showTypingIndicator()
                            sendButton.isEnabled = false
                            messageEditText.isEnabled = false
                        }

                        is Resource.Success -> {
                            hideTypingIndicator()
                            val messageList = resource.data
                            if (messageList.isEmpty()) {
                                binding.textEmpty.visibility = View.VISIBLE
                            } else {
                                binding.textEmpty.visibility = View.GONE
                                chatAdapter.submitMessage(messageList.last())
                                recyclerView.smoothScrollToPosition(chatAdapter.itemCount - 1)

                                if (messageList.last().fromAI) {
                                    AppLogger.d(messageList.last().text)
                                }
                            }
                        }

                        is Resource.Error -> {
                            hideTypingIndicator()
                            sendButton.isEnabled = true
                            messageEditText.isEnabled = true
                            chatAdapter.submitMessage(Message(resource.message, true))
                            recyclerView.smoothScrollToPosition(chatAdapter.itemCount - 1)
                        }
                    }
                }
            }

            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel
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

    private fun finishDebate() {
        messageEditText.hint = getString(R.string.debate_finished)
        messageEditText.isEnabled = false
        sendButton.setImageResource(R.drawable.ic_check)
    }

    private fun clearFocus() {
        messageEditText.clearFocus()

        val imm =
            requireContext().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(view?.windowToken, 0)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}