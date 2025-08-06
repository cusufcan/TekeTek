package com.cusufcan.teketek.ui.presentation.summary

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.cusufcan.teketek.databinding.FragmentSummaryBinding

class SummaryFragment : Fragment() {
    private var _binding: FragmentSummaryBinding? = null
    private val binding get() = _binding!!

    private lateinit var topicText: TextView
    private lateinit var summaryText: TextView

    private val args: SummaryFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        _binding = FragmentSummaryBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bindViews()
        bindValues()
    }

    private fun bindViews() {
        topicText = binding.topicText
        summaryText = binding.summaryText
    }

    private fun bindValues() {
        topicText.text = args.topic.title
        summaryText.text = args.summary.summary
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}