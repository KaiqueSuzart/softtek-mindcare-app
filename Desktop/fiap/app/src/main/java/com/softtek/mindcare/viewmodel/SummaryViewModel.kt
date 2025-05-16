package com.softtek.mindcare.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.softtek.mindcare.model.Answer
import com.softtek.mindcare.model.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class SummaryViewModel : ViewModel() {
    private val _submitState = MutableStateFlow<UiState<Unit>>(UiState.Success(Unit))
    val submitState: StateFlow<UiState<Unit>> = _submitState

    fun submitSummary(answers: List<Answer>) = viewModelScope.launch {
        _submitState.value = UiState.Loading
        try {
            // TODO: Implementar envio do resumo para o backend
            _submitState.value = UiState.Success(Unit)
        } catch (e: Exception) {
            _submitState.value = UiState.Error("Erro ao enviar resumo: ${e.message}")
        }
    }
} 