package com.example.open_ai_sample.ui

data class MainUiState(
    val prompt: String = "",
    val sendResultState: SendResultState = SendResultState.NotYet,
) {

    val isEnablePrompt = (sendResultState is SendResultState.Loading).not()
    val isEnableSend = prompt.isNotBlank() && (sendResultState is SendResultState.Loading).not()

    sealed interface SendResultState {
        object NotYet : SendResultState
        object Loading : SendResultState
        data class Success(val results: List<String>) : SendResultState
        data class Error(val message: String) : SendResultState
    }
}
