package com.example.cardcharity.presentation.activities.reset


import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.cardcharity.R
import com.example.cardcharity.presentation.theme.PreviewTheme
import com.example.cardcharity.presentation.ui.elements.*


@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun ResetPasswordScreen(
    reduce: (event: ResetEvent) -> Unit,
    viewState: ResetViewState,
    initialEmail: String = ""

) {
    var email by rememberSaveable { mutableStateOf(initialEmail) }

    val keyboardController = LocalSoftwareKeyboardController.current
    val focusManager = LocalFocusManager.current

    val dialogIsOpen = remember { mutableStateOf(false) }

    ResetPasswordDialog(
        isOpen = dialogIsOpen,
        email = email,
        onClick = { reduce(finish()) }
    )

    LaunchedEffect(viewState) {
        if (viewState is Success) {
            dialogIsOpen.value = true
        }
    }

    viewState.commonFailMessageOrNull()?.let {
        Toast.makeText(LocalContext.current, it, Toast.LENGTH_LONG).show()
    }

    Surface(
        color = MaterialTheme.colors.background,
        modifier = Modifier.fillMaxSize()
    ) {
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            BackButton(onClick = { reduce(finish()) })
            NestedResizeColumn {
                ResetPasswordLabel()

                EmailTextField(
                    email = email,
                    onEmailChange = { email = it },
                    focusManager = focusManager,
                    isError = viewState.failOrNot(Locale.EMAIL),
                    helperText = viewState.failMessageOrEmpty(Locale.EMAIL),
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Email,
                        imeAction = ImeAction.Done,
                    ),
                    keyboardActions = KeyboardActions(
                        onDone = {
                            keyboardController?.hide()
                            focusManager.clearFocus()
                        }
                    )
                )

                ResetPasswordButton(
                    viewState = viewState,
                    onClick = { reduce(resetPassword(email = email)) }
                )

                ResetPasswordMessage()
            }
        }
    }
}

@Composable
fun ResetPasswordMessage() {
    Text(
        text = stringResource(R.string.reset_text),
        fontSize = 14.sp,
        lineHeight = 20.sp,
        textAlign = TextAlign.Center,
        modifier = Modifier
            .padding(horizontal = 24.dp)
            .padding(bottom = 16.dp)
            .fillMaxWidth()

    )
}

@Composable
fun ResetPasswordButton(
    viewState: ResetViewState,
    onClick: () -> Unit
) {
    AppButton(
        onClick = onClick,
        backgroundColor = MaterialTheme.colors.primary,
        modifier = Modifier
            .padding(horizontal = 24.dp)
            .padding(bottom = 16.dp, top = 24.dp)
            .fillMaxWidth()
    ) {
        when (viewState) {
            Load -> {
                CircularProgressIndicator(color = MaterialTheme.colors.onPrimary)
            }

            is Success -> {
                Icon(
                    painter = painterResource(R.drawable.ic_done_all_24),
                    tint = MaterialTheme.colors.onPrimary,
                    contentDescription = null
                )
            }

            else -> {
                ButtonText(
                    text = stringResource(R.string.send_mail),
                    color = MaterialTheme.colors.onPrimary
                )

                Icon(
                    painter = painterResource(R.drawable.ic_send_24),
                    tint = MaterialTheme.colors.onPrimary,
                    contentDescription = null,
                    modifier = Modifier.padding(start = 8.dp)
                )
            }
        }
    }
}


@Composable
fun ResetPasswordLabel() {
        Text(
            text = stringResource(R.string.reset_password),
            style = MaterialTheme.typography.h3,
            fontWeight = FontWeight.Bold,
            fontSize = 24.sp,
            modifier = Modifier
                .padding(horizontal = 24.dp)
                .padding(bottom = 24.dp, top = 24.dp)
        )
}

@Preview(showSystemUi = true, showBackground = true)
@Composable
fun ResetPasswordPreview() {
    PreviewTheme {
        ResetPasswordScreen(
            reduce = {},
            viewState = default()
        )
    }
}
