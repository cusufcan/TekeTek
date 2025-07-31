package com.cusufcan.teketek.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cusufcan.teketek.data.model.DebateRequest
import com.cusufcan.teketek.domain.model.Message
import com.cusufcan.teketek.domain.usecase.GetCounterArgumentUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DebateViewModel @Inject constructor(
    private val getCounterArgumentUseCase: GetCounterArgumentUseCase,
) : ViewModel() {
    private val _messages = MutableStateFlow<List<Message>>(emptyList())
    val messages: StateFlow<List<Message>> get() = _messages.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> get() = _isLoading.asStateFlow()

    fun sendUserMessage(request: DebateRequest) {
        appendMessage(Message(request.userArgument, fromAI = false))

        viewModelScope.launch {
            _isLoading.value = true
            val aiReply = getCounterArgumentUseCase(request)
            appendMessage(Message(aiReply.counterArgument, fromAI = true))
            _isLoading.value = false
        }
    }

    private fun appendMessage(message: Message) {
        _messages.update { oldList -> oldList + message }
    }
}