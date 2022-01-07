package com.example.cardcharity.presentation.activities.auth.login


import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.ClickableText
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material.*
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment

import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.cardcharity.R
import com.example.cardcharity.presentation.theme.PreviewTheme
import com.example.cardcharity.presentation.ui.elements.*
import timber.log.Timber


/*
- signup button
- google button
 */

val LOCALE_EMAIL = LoginViewState.Fail.Locale.EMAIL
val LOCALE_PASSWORD = LoginViewState.Fail.Locale.PASSWORD
val LOCALE_COMMON = LoginViewState.Fail.Locale.COMMON

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun LoginScreen(
    reduce: (event: LoginEvent) -> Unit,
    viewState: LoginViewState
) {
    val context = LocalContext.current
    var email by rememberSaveable { mutableStateOf("") }
    var password by rememberSaveable { mutableStateOf("") }

    //Common errors handling
    viewState.commonFailMessageOrNull()?.let { errorMessage ->
        Toast.makeText(context, errorMessage, Toast.LENGTH_LONG).show()
    }

    Surface(
        color = MaterialTheme.colors.background,
        modifier = Modifier.fillMaxSize()
    ) {
        val keyboardController = LocalSoftwareKeyboardController.current
        val focusManager = LocalFocusManager.current

        NestedResizeColumn {
            LoginLabel()

            EmailTextField(
                email = email,
                onEmailChange = { email = it },
                isError = viewState.failOrNot(LOCALE_EMAIL),
                helperText = viewState.failMessageOrEmpty(LOCALE_EMAIL),
                focusManager = focusManager
            )

            VerticalSpace(8.dp)

            PasswordTextField(
                password = password,
                onPasswordChange = { password = it },
                isError = viewState.failOrNot(LOCALE_PASSWORD),
                helperText = viewState.failMessageOrEmpty(LOCALE_PASSWORD),
                keyboardActions = KeyboardActions(
                    onDone = {
                        keyboardController?.hide()
                        focusManager.clearFocus()
                    }
                )
            )

            Box(
                modifier = Modifier
                    .padding(horizontal = 24.dp)
                    .fillMaxWidth()
            ) {
                ForgotPasswordButton(
                    onClick = { reduce(LoginEvent.forgotPassword(email)) },
                    modifier = Modifier
                        .padding(end = 16.dp)
                        .align(Alignment.CenterEnd)
                )
            }

            VerticalSpace(56.dp)
            LoginButton(
                state = viewState,
                onClick = {
                    reduce(
                        LoginEvent.loginWithEmailAndPassword(
                            email = email,
                            password = password
                        )
                    )
                }
            )

            SignupButton(
                onClick = { reduce(LoginEvent.signup()) }
            )

            Divider(modifier = Modifier.padding(horizontal = 128.dp))

            VerticalSpace(56.dp)

            GoogleButton(
                onClick = { reduce(LoginEvent.loginWithGoogle()) }
            )
        }
    }
}

@Composable
fun SignupButton(onClick: () -> Unit) {
    val context = LocalContext.current
    val tag = "Signup"
    Box(modifier = Modifier.fillMaxWidth()) {
        val text = buildAnnotatedString {
            withStyle(
                style = SpanStyle(
                    color = MaterialTheme.colors.onBackground.copy(alpha = 0.72F),
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Normal
                )
            ) {
                append("${context.getString(R.string.do_not_have_any_account)} ")
            }

            pushStringAnnotation(
                tag = tag,
                annotation = tag
            )

            withStyle(
                style = SpanStyle(
                    color = MaterialTheme.colors.primary,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold
                )
            ) {
                append(context.getString(R.string.sign_up))
            }

            pop()
        }

        ClickableText(
            text = text,
            onClick = { offset ->
                text.getStringAnnotations(
                    tag = tag,
                    start = offset,
                    end = offset
                ).firstOrNull()?.let { annotation ->
                    if (annotation.item == tag) {
                        onClick.invoke()
                    }
                }
            },
            modifier = Modifier
                .padding(all = 24.dp)
                .align(Alignment.Center)

        )
    }
}

@Composable
fun GoogleButton(onClick: () -> Unit) {
    AppButton(
        onClick = onClick,
        backgroundColor = Color.White,
        modifier = Modifier
            .padding(horizontal = 24.dp)
            .padding(bottom = 24.dp)
            .fillMaxWidth()
    ) {

        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {

            Text(
                text = stringResource(R.string.login_google),
                color = Color.Black,
                style = MaterialTheme.typography.body1,
                fontWeight = FontWeight.Medium
            )

            Icon(
                painter = painterResource(R.drawable.ic_google_24),
                tint = Color.Unspecified,
                contentDescription = null
            )
        }
    }
}

@Composable
fun LoginButton(
    onClick: () -> Unit,
    state: LoginViewState
) {
    AppButton(
        onClick = {
            if (state != LoginViewState.Load && state !is LoginViewState.Success) {
                onClick()
            }
        },
        backgroundColor = MaterialTheme.colors.primary,
        modifier = Modifier
            .padding(horizontal = 24.dp)
            .fillMaxWidth()
    ) {
        when (state) {
            LoginViewState.Load -> {
                CircularProgressIndicator(
                    color = MaterialTheme.colors.onPrimary
                )
            }

            is LoginViewState.Success -> {
                Icon(
                    painter = painterResource(R.drawable.ic_done_24),
                    tint = MaterialTheme.colors.onPrimary,
                    contentDescription = null
                )
            }

            else -> {
                ButtonText(
                    text = stringResource(R.string.log_in),
                    color = MaterialTheme.colors.onPrimary
                )
            }
        }
    }
}


@Composable
fun LoginLabel() {
    val context = LocalContext.current
    Text(
        text = buildAnnotatedString {
            with(context) {
                withStyle(
                    style = SpanStyle(
                        color = MaterialTheme.colors.onBackground,
                        fontSize = 38.sp,
                        fontWeight = FontWeight.Light
                    )
                ) {
                    append("${getString(R.string.welcome_back)},\n")
                    append(getString(R.string.to))
                }

                withStyle(
                    style = SpanStyle(
                        color = MaterialTheme.colors.onBackground,
                        fontSize = 38.sp,
                        fontWeight = FontWeight.Bold
                    )
                ) {
                    append(" ${getString(R.string.app_name)}!")
                }
            }
        },
        modifier = Modifier
            .padding(horizontal = 24.dp)
            .padding(
                top = 56.dp,
                bottom = 56.dp
            )
    )
}


@Preview(showBackground = true, showSystemUi = true)
@Composable
fun LoginScreenPreview() {
    PreviewTheme {
        LoginScreen(
            reduce = {},
            LoginViewState.default()
        )
    }
}
