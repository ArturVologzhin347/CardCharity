package com.example.cardcharity.presentation.activities.password.reset


import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.platform.SoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.cardcharity.R
import com.example.cardcharity.presentation.theme.PreviewTheme
import com.example.cardcharity.presentation.ui.elements.*


@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun ResetPasswordScreen(
    reduce: (event: ResetPasswordEvent) -> Unit,
    viewState: ResetPasswordViewState

) {
    var oldPassword by rememberSaveable { mutableStateOf("") }
    var newPassword by rememberSaveable { mutableStateOf("") }
    var repeatPassword by rememberSaveable { mutableStateOf("") }


    val keyboardController = LocalSoftwareKeyboardController.current
    val focusManager = LocalFocusManager.current


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

                OldPasswordField(
                    value = oldPassword,
                    onOldPasswordChange = { oldPassword = it },
                    isError = viewState.failOrNot(Fail.Locale.OLD_PASSWORD),
                    helperText = viewState.failMessageOrEmpty(Fail.Locale.OLD_PASSWORD),
                    focusManager = focusManager
                )

                VerticalSpace(dp = 8.dp)

                NewPasswordField(
                    value = newPassword,
                    onNewPasswordChange = { newPassword = it },
                    isError = viewState.failOrNot(Fail.Locale.NEW_PASSWORD),
                    helperText = viewState.failMessageOrNull(Fail.Locale.NEW_PASSWORD)
                        ?: stringResource(R.string.password_help),
                    focusManager = focusManager
                )

                VerticalSpace(24.dp)

                RepeatPasswordField(
                    value = repeatPassword,
                    onRepeatPasswordChange = { repeatPassword = it },
                    isError = viewState.failOrNot(Fail.Locale.REPEAT_NEW_PASSWORD),
                    helperText = viewState.failMessageOrEmpty(Fail.Locale.NEW_PASSWORD),
                    keyboardController = keyboardController,
                    focusManager = focusManager
                )

                ResetPasswordButton(
                    viewState = viewState,
                    onClick = {
                        reduce(
                            resetPassword(
                                oldPassword = oldPassword,
                                newPassword = newPassword,
                                repeatPassword = repeatPassword
                            )
                        )
                    }
                )

            }
        }
    }
}


@Composable
fun ResetPasswordButton(
    viewState: ResetPasswordViewState,
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

            is Success -> {
                Icon(
                    painter = painterResource(R.drawable.ic_done_all_24),
                    tint = MaterialTheme.colors.onPrimary,
                    contentDescription = null
                )
            }

            else -> {
                ButtonText(
                    text = stringResource(R.string.reset),
                    color = MaterialTheme.colors.onPrimary
                )
            }
        }
    }
}


@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun RepeatPasswordField(
    value: String,
    onRepeatPasswordChange: (repeatPassword: String) -> Unit,
    isError: Boolean,
    helperText: String,
    keyboardController: SoftwareKeyboardController?,
    focusManager: FocusManager
) {
    AuthTextField(
        value = value,
        onValueChange = onRepeatPasswordChange,
        label = stringResource(R.string.password_repeat),
        visualTransformation = PasswordVisualTransformation(),
        isError = isError,
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Password,
            imeAction = ImeAction.Done
        ),
        keyboardActions = KeyboardActions(
            onDone = {
                keyboardController?.hide()
                focusManager.clearFocus()
            }
        ),
        paddingHorizontal = 16.dp
    )

    AuthHelperText(
        text = helperText,
        isError = isError
    )
}

@Composable
fun NewPasswordField(
    value: String,
    onNewPasswordChange: (newPassword: String) -> Unit,
    isError: Boolean,
    helperText: String,
    focusManager: FocusManager
) {
    var shown by rememberSaveable { mutableStateOf(false) }

    AuthTextField(
        value = value,
        onValueChange = onNewPasswordChange,
        label = stringResource(R.string.password_new),
        visualTransformation = if (shown) VisualTransformation.None else PasswordVisualTransformation(),
        isError = isError,
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Password,
            imeAction = ImeAction.Next
        ),
        keyboardActions = KeyboardActions(
            onNext = {
                focusManager.moveFocus(FocusDirection.Down)
            }
        ),
        trailingIcon = {
            IconButton(onClick = { shown = !shown }) {
                Icon(
                    painter = painterResource(if (shown) IC_VISIBILITY_OFF else IC_VISIBILITY_ON),
                    contentDescription = null
                )
            }
        },
        paddingHorizontal = 16.dp
    )

    AuthHelperText(
        text = helperText,
        isError = isError
    )
}


@Composable
fun OldPasswordField(
    value: String,
    onOldPasswordChange: (oldPassword: String) -> Unit,
    isError: Boolean,
    helperText: String,
    focusManager: FocusManager
) {
    AuthTextField(
        value = value,
        onValueChange = onOldPasswordChange,
        label = stringResource(R.string.password_old),
        visualTransformation = PasswordVisualTransformation(),
        isError = isError,
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Password,
            imeAction = ImeAction.Next
        ),
        keyboardActions = KeyboardActions(
            onNext = {
                focusManager.moveFocus(FocusDirection.Down)
            }
        ),
        paddingHorizontal = 16.dp
    )

    AuthHelperText(
        text = helperText,
        isError = isError
    )
}

@Composable
fun ResetPasswordLabel() {
    Box(modifier = Modifier.fillMaxWidth()) {
        Text(
            text = stringResource(R.string.reset_password),
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
fun ResetPasswordPreview() {
    PreviewTheme {
        ResetPasswordScreen(
            reduce = {},
            viewState = default()
        )
    }
}
