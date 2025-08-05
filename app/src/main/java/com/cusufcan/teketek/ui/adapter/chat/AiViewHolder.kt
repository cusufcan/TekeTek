package com.cusufcan.teketek.ui.adapter.chat

import androidx.recyclerview.widget.RecyclerView
import com.cusufcan.teketek.databinding.ItemMessageAiBinding
import com.cusufcan.teketek.domain.model.Message
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class AiViewHolder(
    private val binding: ItemMessageAiBinding,
    private val scope: CoroutineScope,
    private val recyclerView: RecyclerView,
    private val onCompletion: () -> Unit,
) : RecyclerView.ViewHolder(binding.root) {
    private var typingJob: Job? = null

    fun bind(message: Message) {
        typingJob?.cancel()

        if (!message.animated) {
            binding.messageText.text = ""
            typingJob = scope.launch {
                for (i in message.text.indices) {
                    binding.messageText.text = message.text.substring(0, i + 1)
                    delay(10L)
                    recyclerView.scrollToPosition(absoluteAdapterPosition)
                }
                message.animated = true
                onCompletion()
            }
        }
    }
}