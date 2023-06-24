package com.example.open_ai_sample

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import com.example.open_ai_sample.ui.MainScreen
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MainScreen(
                uiState = viewModel.uiState,
                onValueChangePrompt = viewModel::onValueChangePrompt,
                onClickSend = viewModel::onClickSend,
            )
        }
    }
}