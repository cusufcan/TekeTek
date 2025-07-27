package com.cusufcan.teketek.ui.adapter.topic

import androidx.recyclerview.widget.RecyclerView
import com.cusufcan.teketek.databinding.ItemTopicBinding
import com.cusufcan.teketek.domain.model.Topic

class TopicViewHolder(
    private val binding: ItemTopicBinding,
    private val onTopicClick: (topic: Topic) -> Unit,
) : RecyclerView.ViewHolder(binding.root) {
    fun bind(topic: Topic) {
        binding.textTitle.text = topic.title
        binding.textDescription.text = topic.description
        binding.root.setOnClickListener {
            onTopicClick(topic)
        }
    }
}