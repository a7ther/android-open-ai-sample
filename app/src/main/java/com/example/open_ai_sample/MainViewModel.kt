package com.example.open_ai_sample

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.open_ai_sample.network.RetrofitOpenAiNetwork
import com.example.open_ai_sample.network.model.chatcompletions.ChatCompletions
import com.example.open_ai_sample.network.model.chatcompletions.child.ChatMessage
import com.example.open_ai_sample.network.model.chatcompletions.child.ChatRole
import com.example.open_ai_sample.ui.MainUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val retrofitOpenAiNetwork: RetrofitOpenAiNetwork,
    private val ioDispatcher: CoroutineDispatcher
) : ViewModel() {

    var uiState by mutableStateOf(MainUiState())
        private set

    fun onValueChangePrompt(prompt: String) {
        uiState = uiState.copy(prompt = prompt)
    }

    fun onClickSend() {
        viewModelScope.launch {
            uiState = uiState.copy(sendResultState = MainUiState.SendResultState.Loading)
            
            val result = withContext(ioDispatcher) {
                retrofitOpenAiNetwork.chatCompletions(createRequest(uiState.prompt))
            }

            uiState = when (result) {
                is ChatCompletions.Response.Success -> {
                    uiState.copy(
                        sendResultState = MainUiState.SendResultState.Success(
                            result.choices.map { it.message?.content ?: "" }
                        )
                    )
                }

                is ChatCompletions.Response.Failure -> {
                    uiState.copy(
                        sendResultState = MainUiState.SendResultState.Error(
                            result.exception.message ?: "unknown error"
                        )
                    )
                }
            }
        }
    }

    /**
     * ここでプロンプトをカスタムしてください
     * api reference : https://platform.openai.com/docs/api-reference/chat/create
     */
    private fun createRequest(prompt: String) = ChatCompletions.Request(
        model = "gpt-3.5-turbo",
        messages = listOf(
            ChatMessage(
                role = ChatRole.SYSTEM.role,
                content = "You are a helpful assistant.",
            ),
            ChatMessage(
                role = ChatRole.USER.role,
                content = prompt,
            )
        ),
        temperature = 0.0,
        n = 5,
    )
}