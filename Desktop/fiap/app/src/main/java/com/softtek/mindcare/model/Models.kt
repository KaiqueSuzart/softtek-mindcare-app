package com.softtek.mindcare.model

import kotlinx.serialization.Serializable

@Serializable
data class Question(
    val id: String,
    val text: String,
    val options: List<String>
)

@Serializable
data class Answer(
    val questionId: String,
    val questionText: String,
    val selectedOption: String
)

sealed class UiState<out T> {
    data object Loading : UiState<Nothing>()
    data class Success<T>(val data: T) : UiState<T>()
    data class Error(val message: String) : UiState<Nothing>()
} 