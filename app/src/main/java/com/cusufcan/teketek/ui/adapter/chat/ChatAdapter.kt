package com.cusufcan.teketek.ui.adapter.chat

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.cusufcan.teketek.databinding.ItemMessageAiBinding
import com.cusufcan.teketek.databinding.ItemMessageUserBinding
import com.cusufcan.teketek.domain.model.Message
import kotlinx.coroutines.CoroutineScope

class ChatAdapter(
    private val scope: CoroutineScope,
    private val recyclerView: RecyclerView,
    private val onCompletion: () -> Unit = { },
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private val messages = mutableListOf<Message>()

    companion object {
        private const val TYPE_USER = 0
        private const val TYPE_AI = 1
    }

    fun submitMessage(message: Message) {
        messages.add(message)
        notifyItemInserted(messages.lastIndex)
    }

    override fun getItemViewType(position: Int): Int {
        return if (messages[position].fromAI) TYPE_AI else TYPE_USER
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return if (viewType == TYPE_USER) {
            val binding = ItemMessageUserBinding.inflate(inflater, parent, false)
            UserViewHolder(binding)
        } else {
            val binding = ItemMessageAiBinding.inflate(inflater, parent, false)
            AiViewHolder(binding, scope, recyclerView, onCompletion)
        }
    }

    override fun onBindViewHolder(
        holder: RecyclerView.ViewHolder,
        position: Int,
    ) {
        val msg = messages[position]
        if (holder is UserViewHolder) holder.bind(msg)
        else if (holder is AiViewHolder) holder.bind(msg)
    }

    override fun getItemCount() = messages.size
}