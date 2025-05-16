package com.softtek.mindcare.ui.screen

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.softtek.mindcare.model.Answer
import com.softtek.mindcare.model.UiState
import com.softtek.mindcare.ui.component.PrimaryButton
import com.softtek.mindcare.viewmodel.SummaryViewModel

@Composable
fun SummaryScreen(
    answers: List<Answer>,
    viewModel: SummaryViewModel = viewModel()
) {
    val submitState by viewModel.submitState.collectAsState()
    var showSnackbar by remember { mutableStateOf(false) }
    var snackbarMessage by remember { mutableStateOf("") }

    LaunchedEffect(submitState) {
        when (submitState) {
            is UiState.Success -> {
                showSnackbar = true
                snackbarMessage = "Resumo enviado com sucesso!"
            }
            is UiState.Error -> {
                showSnackbar = true
                snackbarMessage = (submitState as UiState.Error).message
            }
            else -> {}
        }
    }

    Scaffold(
        bottomBar = {
            Surface(
                modifier = Modifier.fillMaxWidth(),
                shadowElevation = 8.dp
            ) {
                PrimaryButton(
                    text = "Concluir",
                    onClick = { viewModel.submitSummary(answers) },
                    enabled = true,
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
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(16.dp)
            ) {
                items(answers) { answer ->
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 8.dp)
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp)
                        ) {
                            Text(
                                text = answer.questionText,
                                style = MaterialTheme.typography.titleMedium
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                            Text(
                                text = answer.selectedOption,
                                style = MaterialTheme.typography.bodyLarge
                            )
                        }
                    }
                }
            }

            if (showSnackbar) {
                Snackbar(
                    modifier = Modifier
                        .align(Alignment.BottomCenter)
                        .padding(16.dp)
                ) {
                    Text(text = snackbarMessage)
                }
            }
        }
    }
} 