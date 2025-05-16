package com.softtek.mindcare.ui.screen

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.softtek.mindcare.model.Question
import com.softtek.mindcare.model.UiState
import com.softtek.mindcare.ui.component.*
import com.softtek.mindcare.viewmodel.QuestionnaireViewModel

@Composable
fun QuestionnaireScreen(
    onNavigateToSummary: (List<Answer>) -> Unit,
    viewModel: QuestionnaireViewModel = viewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    var currentQuestionIndex by remember { mutableStateOf(0) }
    var selectedOptionIndex by remember { mutableStateOf<Int?>(null) }

    LaunchedEffect(Unit) {
        viewModel.loadQuestions()
    }

    Scaffold(
        bottomBar = {
            Surface(
                modifier = Modifier.fillMaxWidth(),
                shadowElevation = 8.dp
            ) {
                PrimaryButton(
                    text = if (currentQuestionIndex == (uiState as? UiState.Success<List<Question>>)?.data?.size?.minus(1)) {
                        "Ver Resumo"
                    } else {
                        "Próximo"
                    },
                    onClick = {
                        if (selectedOptionIndex != null) {
                            val questions = (uiState as? UiState.Success<List<Question>>)?.data
                            questions?.let { qs ->
                                val currentQuestion = qs[currentQuestionIndex]
                                viewModel.addAnswer(
                                    currentQuestion,
                                    currentQuestion.options[selectedOptionIndex!!]
                                )
                                
                                if (currentQuestionIndex == qs.size - 1) {
                                    viewModel.sendAnswers()
                                    onNavigateToSummary(viewModel.answers)
                                } else {
                                    currentQuestionIndex++
                                    selectedOptionIndex = null
                                }
                            }
                        }
                    },
                    enabled = selectedOptionIndex != null,
                    modifier = Modifier.padding(16.dp)
                )
            }
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            when (uiState) {
                is UiState.Loading -> LoadingScreen()
                is UiState.Error -> ErrorScreen(
                    message = (uiState as UiState.Error).message,
                    onRetry = { viewModel.loadQuestions() }
                )
                is UiState.Success -> {
                    val questions = (uiState as UiState.Success<List<Question>>).data
                    LazyColumn(
                        modifier = Modifier.fillMaxSize(),
                        contentPadding = PaddingValues(16.dp)
                    ) {
                        itemsIndexed(questions) { index, question ->
                            if (index == currentQuestionIndex) {
                                QuestionCard(
                                    question = question.text,
                                    options = question.options,
                                    selectedIndex = selectedOptionIndex,
                                    onSelect = { selectedOptionIndex = it }
                                )
                            }
                        }
                    }
                }
            }
        }
    }
} 