package com.cusufcan.teketek.ui.adapter.chat

import androidx.recyclerview.widget.RecyclerView
import com.cusufcan.teketek.databinding.ItemMessageUserBinding
import com.cusufcan.teketek.domain.model.Message

class UserViewHolder(
    private val binding: ItemMessageUserBinding,
) : RecyclerView.ViewHolder(binding.root) {
    fun bind(message: Message) {
        binding.messageText.text = message.text
    }
}