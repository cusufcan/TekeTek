package com.cusufcan.teketek.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cusufcan.teketek.domain.model.Topic
import com.cusufcan.teketek.domain.usecase.topic.GetTopicsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TopicViewModel @Inject constructor(
    private val getTopicsUseCase: GetTopicsUseCase,
) : ViewModel() {
    private val _topics = MutableStateFlow<List<Topic>>(emptyList())
    val topics: StateFlow<List<Topic>> get() = _topics.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> get() = _isLoading.asStateFlow()

    fun loadTopics() {
        viewModelScope.launch {
            _isLoading.value = true
            val result = getTopicsUseCase()
            _topics.value = result
            _isLoading.value = false
        }
    }
}