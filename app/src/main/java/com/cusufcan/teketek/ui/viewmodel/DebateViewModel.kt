package com.cusufcan.teketek.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cusufcan.teketek.data.model.DebateRequest
import com.cusufcan.teketek.domain.model.Message
import com.cusufcan.teketek.domain.usecase.GetCounterArgumentUseCase
import com.cusufcan.teketek.ui.event.DebateUiEvent
import com.cusufcan.teketek.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DebateViewModel @Inject constructor(
    private val getCounterArgumentUseCase: GetCounterArgumentUseCase,
) : ViewModel() {
    private val _uiState = MutableStateFlow<Resource<List<Message>>>(Resource.Success(emptyList()))
    val uiState: StateFlow<Resource<List<Message>>> get() = _uiState.asStateFlow()

    private val _eventFlow = MutableStateFlow<DebateUiEvent?>(null)
    val eventFlow: StateFlow<DebateUiEvent?> get() = _eventFlow.asStateFlow()

    private var roundCount = 0

    fun sendUserMessage(request: DebateRequest) {
        val currentMessages = (_uiState.value as? Resource.Success)?.data ?: emptyList()
        _uiState.value =
            Resource.Success(currentMessages + Message(request.userArgument, fromAI = false))

        viewModelScope.launch {
            _uiState.value = Resource.Loading()

            try {
                val aiReply = getCounterArgumentUseCase(request)
                val updatedMessages = (_uiState.value as? Resource.Success)?.data ?: emptyList()
                _uiState.value = Resource.Success(
                    updatedMessages + Message(
                        aiReply.counterArgument, fromAI = true
                    )
                )

                roundCount++
                if (roundCount >= 3) {
                    _eventFlow.value = DebateUiEvent.FinishDebate
                }
            } catch (e: Exception) {
                _uiState.value = Resource.Error("Hata oluştu: ${e.localizedMessage}")
            }
        }
    }
}