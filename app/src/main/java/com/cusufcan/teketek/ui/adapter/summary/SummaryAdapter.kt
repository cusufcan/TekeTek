package com.cusufcan.teketek.ui.adapter.summary

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.cusufcan.teketek.databinding.ItemSummaryBinding

class SummaryAdapter(
    private val summaries: List<String>,
    private val context: Context,
    private val isStrengths: Boolean,
) : RecyclerView.Adapter<SummaryViewHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): SummaryViewHolder {
        val binding = ItemSummaryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return SummaryViewHolder(binding, context, isStrengths)
    }

    override fun onBindViewHolder(
        holder: SummaryViewHolder,
        position: Int,
    ) {
        val actualPos = position % summaries.size
        holder.bind(summaries[actualPos])
    }

    override fun getItemCount(): Int {
        return Int.MAX_VALUE
    }
}