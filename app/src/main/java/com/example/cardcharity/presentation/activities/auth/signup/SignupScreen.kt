package com.example.cardcharity.presentation.activities.auth.signup

import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
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
fun SignupScreen(
    reduce: (event: SignupEvent) -> Unit,
    viewState: SignupViewState
) {

    val context = LocalContext.current
    var email by rememberSaveable { mutableStateOf("") }
    var password by rememberSaveable { mutableStateOf("") }
    var confirm by rememberSaveable { mutableStateOf("") }

    viewState.commonFailMessageOrNull()?.let { errorMessage ->
        Toast.makeText(context, errorMessage, Toast.LENGTH_LONG).show()
    }

    Surface(
        color = MaterialTheme.colors.background,
        modifier = Modifier.fillMaxSize()
    ) {
        val keyboardController = LocalSoftwareKeyboardController.current
        val focusManager = LocalFocusManager.current

        Column {
            BackButton(onClick = { reduce(back()) })

            NestedResizeColumn {
                SignupLabel()

                EmailTextField(
                    email = email,
                    onEmailChange = { email = it },
                    isError = viewState.failOrNot(Locale.EMAIL),
                    helperText = viewState.failMessageOrEmpty(Locale.EMAIL),
                    focusManager = focusManager
                )

                VerticalSpace(8.dp)

                PasswordTextField(
                    password = password,
                    onPasswordChange = { password = it },
                    helperText = viewState.failMessageOrNull(Locale.PASSWORD)
                        ?: stringResource(R.string.password_help),
                    isError = viewState.failOrNot(Locale.PASSWORD),
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Password,
                        imeAction = ImeAction.Next
                    ),
                    keyboardActions = KeyboardActions(
                        onNext = {
                            keyboardController?.hide()
                            focusManager.moveFocus(FocusDirection.Down)
                        }
                    )
                )

                VerticalSpace(24.dp)


                val isConfirmError = viewState.failOrNot(Locale.CONFIRM)
                LaunchedEffect(isConfirmError) {
                    if (isConfirmError) {
                        confirm = ""
                    }
                }

                ConfirmPasswordTextField(
                    password = confirm,
                    onPasswordChange = { confirm = it },
                    isError = isConfirmError,
                    helperText = viewState.failMessageOrEmpty(Locale.CONFIRM),
                    keyboardActions = KeyboardActions(
                        onDone = {
                            keyboardController?.hide()
                            focusManager.clearFocus()
                        }
                    )
                )

                SignupButton(
                    viewState = viewState,
                    onClick = {
                        reduce(
                            signup(
                                email = email,
                                password = password,
                                confirm = confirm
                            )
                        )
                    }
                )

                UnderButtonText()
            }
        }
    }
}


@Composable
fun UnderButtonText() {
    Text(
        text = stringResource(R.string.lorem_ipsum),
        style = MaterialTheme.typography.caption,
        textAlign = TextAlign.Center,
        modifier = Modifier.padding(all = 24.dp)
    )
}

@Composable
fun SignupButton(
    viewState: SignupViewState,
    onClick: () -> Unit
) {
    AppButton(
        onClick = onClick,
        backgroundColor = MaterialTheme.colors.primary,
        modifier = Modifier
            .padding(horizontal = 24.dp)
            .padding(top = 16.dp)
            .fillMaxWidth()
    ) {
        when (viewState) {
            Load -> {
                CircularProgressIndicator(
                    color = MaterialTheme.colors.onPrimary
                )
            }

            Success -> {
                Icon(
                    painter = painterResource(R.drawable.ic_done_all_24),
                    tint = MaterialTheme.colors.onPrimary,
                    contentDescription = null
                )
            }

            else -> {
                ButtonText(
                    text = stringResource(R.string.create_account),
                    color = MaterialTheme.colors.onPrimary
                )
            }
        }
    }
}

@Composable
fun SignupLabel() {
    Box(modifier = Modifier.fillMaxWidth()) {
        Text(
            text = stringResource(R.string.sign_up),
            style = MaterialTheme.typography.h3,
            fontWeight = FontWeight.Bold,
            fontSize = 38.sp,
            modifier = Modifier
                .padding(horizontal = 24.dp)
                .padding(bottom = 56.dp)
                .align(Alignment.Center)
        )
    }
}

@Preview(showSystemUi = true, showBackground = true)
@Composable
fun SignupScreenPreview() {
    PreviewTheme {
        SignupScreen(
            reduce = {},
            viewState = default()
        )
    }
}
