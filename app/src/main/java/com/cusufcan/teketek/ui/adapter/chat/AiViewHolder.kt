package com.cusufcan.teketek.ui.adapter.chat

import androidx.recyclerview.widget.RecyclerView
import com.cusufcan.teketek.databinding.ItemMessageAiBinding
import com.cusufcan.teketek.domain.model.Message

class AiViewHolder(
    private val binding: ItemMessageAiBinding,
) : RecyclerView.ViewHolder(binding.root) {
    fun bind(message: Message) {
        binding.messageText.text = message.text
    }
}