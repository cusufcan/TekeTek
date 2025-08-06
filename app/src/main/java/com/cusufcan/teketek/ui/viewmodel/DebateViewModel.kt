package com.cusufcan.teketek.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cusufcan.teketek.domain.model.Message
import com.cusufcan.teketek.domain.usecase.debate.EndDebateUseCase
import com.cusufcan.teketek.domain.usecase.debate.NextDebateUseCase
import com.cusufcan.teketek.domain.usecase.debate.StartDebateUseCase
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
    private val startDebate: StartDebateUseCase,
    private val nextDebate: NextDebateUseCase,
    private val endDebate: EndDebateUseCase,
) : ViewModel() {
    private val _uiState = MutableStateFlow<Resource<List<Message>>>(Resource.Success(emptyList()))
    val uiState: StateFlow<Resource<List<Message>>> = _uiState.asStateFlow()

    private val _eventFlow = MutableStateFlow<DebateUiEvent?>(null)
    val eventFlow: StateFlow<DebateUiEvent?> = _eventFlow.asStateFlow()

    private var debateId: String? = null

    fun start(topic: String) {
        viewModelScope.launch {
            try {
                val res = startDebate(topic)
                debateId = res.debateId
                _uiState.value = Resource.Success(emptyList())
            } catch (e: Exception) {
                _uiState.value = Resource.Error(
                    message = "Başlama hatası: ${e.localizedMessage}",
                    data = emptyList(),
                )
            }
        }
    }

    fun next(userArgument: String) {
        debateId?.let { id ->
            val currentMessages = (_uiState.value as? Resource.Success)?.data ?: emptyList()

            val newMessages = currentMessages + Message(userArgument, fromAI = false)
            _uiState.value = Resource.Success(newMessages)

            viewModelScope.launch {
                _uiState.value = Resource.Loading(newMessages)

                try {
                    val res = nextDebate(id, userArgument)

                    if (res.counterArgument.isNullOrEmpty() || res.turn == null) {
                        throw Exception("Counter argument is null or empty")
                    }

                    val updatedMessages = newMessages + Message(
                        res.counterArgument.trim(),
                        fromAI = true,
                    )
                    _uiState.value = Resource.Success(updatedMessages)

                    if (res.turn >= 3) {
                        end()
                    }
                } catch (e: Exception) {
                    _uiState.value = Resource.Error(
                        message = "Tur hatası: ${e.localizedMessage}",
                        data = newMessages,
                    )
                }
            }
        }
    }

    fun end() {
        viewModelScope.launch {
            debateId?.let { id ->
                try {
                    val res = endDebate(id)
                    _eventFlow.value = DebateUiEvent.FinishDebate(
                        debateId = id,
                        summary = res.summary,
                        strengths = res.strengths,
                        weaknesses = res.weaknesses,
                        message = res.message,
                    )
                    debateId = null
                } catch (e: Exception) {
                    _uiState.value = Resource.Error(
                        message = "Bitirme hatası: ${e.localizedMessage}",
                        data = (_uiState.value as? Resource.Success)?.data ?: emptyList(),
                    )
                }
            }
        }
    }
}