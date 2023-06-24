package com.example.open_ai_sample.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Send
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.open_ai_sample.R
import com.example.open_ai_sample.ui.theme.OpenaisampleTheme

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun MainScreen(
    uiState: MainUiState,
    onValueChangePrompt: (String) -> Unit = {},
    onClickSend: () -> Unit = {},
) {
    val keyboardController = LocalSoftwareKeyboardController.current

    OpenaisampleTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                OutlinedTextField(
                    value = uiState.prompt,
                    onValueChange = onValueChangePrompt,
                    label = { Text(stringResource(R.string.main_screen_label_input_prompt)) },
                    modifier = Modifier
                        .padding(12.dp)
                        .fillMaxWidth()
                        .height(300.dp),
                    enabled = uiState.isEnablePrompt,
                )
                Button(
                    onClick = {
                        keyboardController?.hide()
                        onClickSend()
                    },
                    modifier = Modifier
                        .padding(12.dp)
                        .fillMaxWidth(),
                    enabled = uiState.isEnableSend
                ) {
                    Icon(
                        Icons.Filled.Send,
                        contentDescription = stringResource(R.string.main_screen_send),
                        modifier = Modifier.size(ButtonDefaults.IconSize)
                    )
                    Spacer(Modifier.size(ButtonDefaults.IconSpacing))
                    Text(stringResource(R.string.main_screen_send))
                }
                ResultItem(uiState.sendResultState)
            }
        }
    }
}

@Composable
fun ResultItem(sendResultState: MainUiState.SendResultState) {
    when (sendResultState) {
        is MainUiState.SendResultState.NotYet -> Unit
        is MainUiState.SendResultState.Loading -> {
            CircularProgressIndicator()
        }

        is MainUiState.SendResultState.Success -> {
            LazyColumn(
                modifier = Modifier
                    .padding(12.dp)
                    .fillMaxWidth()
            ) {

                items(sendResultState.results.size) { index ->
                    Text(
                        modifier = Modifier.padding(8.dp),
                        text = "${index + 1}. ${sendResultState.results[index]}",
                    )
                    Divider()
                }
            }
        }

        is MainUiState.SendResultState.Error -> {
            Text(
                modifier = Modifier
                    .padding(12.dp),
                text = "ERROR : ${sendResultState.message}",
            )
        }
    }
}

/** --- Preview --- */

@Preview(showBackground = true)
@Composable
fun ResultItemSuccessPreview() {
    ResultItem(
        MainUiState.SendResultState.Success(listOf("first content", "second content"))
    )
}

@Preview(showBackground = true)
@Composable
fun ResultItemLoadingPreview() {
    ResultItem(
        MainUiState.SendResultState.Loading
    )
}

@Preview(showBackground = true)
@Composable
fun ResultItemErrorPreview() {
    ResultItem(
        MainUiState.SendResultState.Error("error message")
    )
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun MainScreenSuccessPreview() {
    MainScreen(
        uiState = MainUiState(
            sendResultState = MainUiState.SendResultState.Success(
                listOf(
                    "first content : hoge hoge hoge hoge hoge hoge hoge hoge hoge hoge",
                    "second content :  fuga fuga fuga fuga fuga fuga fuga fuga fuga fuga fuga"
                )
            )
        )
    )
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun MainScreenLoadingPreview() {
    MainScreen(
        uiState = MainUiState(
            sendResultState = MainUiState.SendResultState.Loading
        )
    )
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun MainScreenErrorPreview() {
    MainScreen(
        uiState = MainUiState(
            sendResultState = MainUiState.SendResultState.Error(
                "error message : hoge hoge hoge hoge hoge hoge hoge hoge hoge hoge"
            )
        )
    )
}