package com.softtek.mindcare.viewmodel

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.softtek.mindcare.model.Answer
import com.softtek.mindcare.model.Question
import com.softtek.mindcare.model.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class QuestionnaireViewModel : ViewModel() {
    private val _uiState = MutableStateFlow<UiState<List<Question>>>(UiState.Loading)
    val uiState: StateFlow<UiState<List<Question>>> = _uiState

    val answers = mutableStateListOf<Answer>()

    fun loadQuestions() = viewModelScope.launch {
        // TODO: Implementar carregamento de perguntas do backend
        // Mock data para teste
        val mockQuestions = listOf(
            Question(
                id = "1",
                text = "Como você está se sentindo hoje?",
                options = listOf("Ótimo", "Bom", "Regular", "Ruim")
            ),
            Question(
                id = "2",
                text = "Você dormiu bem na última noite?",
                options = listOf("Sim", "Mais ou menos", "Não")
            )
        )
        _uiState.value = UiState.Success(mockQuestions)
    }

    fun addAnswer(question: Question, selectedOption: String) {
        answers.add(
            Answer(
                questionId = question.id,
                questionText = question.text,
                selectedOption = selectedOption
            )
        )
    }

    fun sendAnswers() = viewModelScope.launch {
        // TODO: Implementar envio de respostas para o backend
    }
} 