package com.cusufcan.teketek.ui.adapter.summary

import android.content.Context
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.cusufcan.teketek.R
import com.cusufcan.teketek.databinding.ItemSummaryBinding

class SummaryViewHolder(
    private val binding: ItemSummaryBinding,
    private val context: Context,
    private val isStrengths: Boolean,
) : RecyclerView.ViewHolder(binding.root) {
    fun bind(summary: String) {
        val backgroundColor = if (isStrengths) {
            ContextCompat.getColor(context, R.color.strength_card_bg)
        } else {
            ContextCompat.getColor(context, R.color.weakness_card_bg)
        }

        binding.root.setCardBackgroundColor(backgroundColor)

        binding.summaryText.text = summary
    }
}