package com.softtek.mindcare

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.softtek.mindcare.model.Answer
import com.softtek.mindcare.theme.MindCareTheme
import com.softtek.mindcare.ui.screen.QuestionnaireScreen
import com.softtek.mindcare.ui.screen.SummaryScreen
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MindCareTheme {
                MindCareApp()
            }
        }
    }
}

@Composable
fun MindCareApp() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = "questionnaire"
    ) {
        composable("questionnaire") {
            QuestionnaireScreen(
                onNavigateToSummary = { answers ->
                    val answersJson = Json.encodeToString(answers)
                    navController.navigate("summary/$answersJson")
                }
            )
        }
        
        composable(
            route = "summary/{answers}",
            arguments = listOf(
                navArgument("answers") {
                    type = NavType.StringType
                }
            )
        ) { backStackEntry ->
            val answersJson = backStackEntry.arguments?.getString("answers") ?: "[]"
            val answers = Json.decodeFromString<List<Answer>>(answersJson)
            SummaryScreen(answers = answers)
        }
    }
} 