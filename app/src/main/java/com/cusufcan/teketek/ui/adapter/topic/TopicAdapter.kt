package com.cusufcan.teketek.ui.adapter.topic

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.cusufcan.teketek.databinding.ItemTopicBinding
import com.cusufcan.teketek.domain.model.Topic

class TopicAdapter(
    private val onTopicClick: (topic: Topic) -> Unit,
) : ListAdapter<Topic, TopicViewHolder>(DiffCallback) {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): TopicViewHolder {
        val binding = ItemTopicBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false,
        )
        return TopicViewHolder(binding, onTopicClick)
    }

    override fun onBindViewHolder(
        holder: TopicViewHolder,
        position: Int,
    ) {
        holder.bind(getItem(position))
    }

    companion object {
        private val DiffCallback = object : DiffUtil.ItemCallback<Topic>() {
            override fun areItemsTheSame(oldItem: Topic, newItem: Topic): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: Topic, newItem: Topic): Boolean {
                return oldItem == newItem
            }
        }
    }
}