package com.cusufcan.teketek.ui.presentation.topic

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.cusufcan.teketek.databinding.FragmentTopicBinding
import com.cusufcan.teketek.ui.adapter.topic.TopicAdapter
import com.cusufcan.teketek.ui.viewmodel.TopicViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class TopicFragment : Fragment() {
    private var _binding: FragmentTopicBinding? = null
    private val binding get() = _binding!!

    private val viewModel: TopicViewModel by viewModels()
    private lateinit var adapter: TopicAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        _binding = FragmentTopicBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bindAdapter()
        observeTopics()
        observeLoading()
        loadTopics()
    }

    fun bindAdapter() {
        adapter = TopicAdapter { selectedTopic ->
            val action = TopicFragmentDirections.actionTopicFragmentToSideFragment(selectedTopic)
            findNavController().navigate(action)
        }

        binding.recyclerViewTopics.adapter = adapter
    }

    fun observeTopics() {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.topics.collectLatest { topics ->
                    adapter.submitList(topics)
                }
            }
        }
    }

    fun observeLoading() {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.isLoading.collectLatest { isLoading ->
                    binding.progressBar.visibility = if (isLoading) {
                        View.VISIBLE
                    } else {
                        View.GONE
                    }
                }
            }
        }
    }

    fun loadTopics() {
        viewModel.loadTopics()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}