package com.example.cardcharity.presentation.ui.elements

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable

import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.cardcharity.R
import com.example.cardcharity.presentation.theme.surface8Primary

const val IC_VISIBILITY_ON = R.drawable.ic_visibility_24
const val IC_VISIBILITY_OFF = R.drawable.ic_visibility_off_24


@Composable
fun ConfirmPasswordTextField(
    password: String,
    onPasswordChange: (password: String) -> Unit,
    isError: Boolean = false,
    helperText: String = "",
    keyboardOptions: KeyboardOptions = KeyboardOptions(
        keyboardType = KeyboardType.Password,
        imeAction = ImeAction.Done
    ),
    keyboardActions: KeyboardActions = KeyboardActions()
) {
    AuthTextField(
        value = password,
        onValueChange = onPasswordChange,
        label = stringResource(R.string.password_confirm),
        isError = isError,
        keyboardActions = keyboardActions,
        keyboardOptions = keyboardOptions,
        visualTransformation = PasswordVisualTransformation(),
        leadingIcon = {
            Icon(
                painter = painterResource(R.drawable.ic_verified_24),
                contentDescription = null
            )
        },
    )

    AuthHelperText(
        text = helperText,
        isError = isError
    )
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun PasswordTextField(
    password: String,
    onPasswordChange: (password: String) -> Unit,
    isError: Boolean = false,
    helperText: String = "",
    keyboardOptions: KeyboardOptions = KeyboardOptions(
        keyboardType = KeyboardType.Password,
        imeAction = ImeAction.Done
    ),
    keyboardActions: KeyboardActions = KeyboardActions()
) {

    var shown by rememberSaveable { mutableStateOf(false) }

    AuthTextField(
        value = password,
        onValueChange = onPasswordChange,
        label = stringResource(R.string.password),
        isError = isError,
        keyboardActions = keyboardActions,
        keyboardOptions = keyboardOptions,
        visualTransformation = if (shown) VisualTransformation.None else PasswordVisualTransformation(),
        leadingIcon = {
            Icon(
                painter = painterResource(R.drawable.ic_lock_24),
                contentDescription = null
            )
        },
        trailingIcon = {
            IconButton(onClick = { shown = !shown }) {
                Icon(
                    painter = painterResource(if (shown) IC_VISIBILITY_OFF else IC_VISIBILITY_ON),
                    contentDescription = null
                )
            }
        }
    )

    AuthHelperText(
        text = helperText,
        isError = isError
    )
}


@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun EmailTextField(
    email: String,
    onEmailChange: (email: String) -> Unit,
    isError: Boolean = false,
    helperText: String = "",
    leadingIcon: @Composable (() -> Unit)? = {
        Icon(
            painter = painterResource(R.drawable.ic_alternate_email_24),
            contentDescription = null
        )
    },
    focusManager: FocusManager
) {
    AuthTextField(
        value = email,
        onValueChange = onEmailChange,
        label = stringResource(R.string.email_address),
        isError = isError,
        leadingIcon = leadingIcon,
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Email,
            imeAction = ImeAction.Next
        ),
        keyboardActions = KeyboardActions(
            onNext = {
                focusManager.moveFocus(FocusDirection.Down)
            }
        )
    )

    AuthHelperText(
        text = helperText,
        isError = isError
    )
}


@Composable
fun AuthHelperText(
    text: String,
    modifier: Modifier = Modifier,
    isError: Boolean = false
) {
    HelperText(
        text = text,
        isError = isError,
        modifier = modifier
            .padding(
                start = 40.dp,
                end = 24.dp,
                top = 2.dp
            )
    )
}

@Composable
fun AuthTextField(
    value: String,
    onValueChange: (value: String) -> Unit,
    label: String,
    modifier: Modifier = Modifier,
    isError: Boolean = false,
    placeholder: @Composable (() -> Unit)? = null,
    leadingIcon: @Composable (() -> Unit)? = null,
    trailingIcon: @Composable (() -> Unit)? = null,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    keyboardActions: KeyboardActions = KeyboardActions(),
    paddingHorizontal: Dp = 24.dp
) {
    AdvancedTextField(
        value = value,
        onValueChange = onValueChange,
        label = label,
        isError = isError,
        placeholder = placeholder,
        leadingIcon = leadingIcon,
        trailingIcon = trailingIcon,
        visualTransformation = visualTransformation,
        keyboardOptions = keyboardOptions,
        keyboardActions = keyboardActions,
        modifier = modifier
            .padding(horizontal = paddingHorizontal)
            .fillMaxWidth()
    )
}


@Composable
fun AdvancedTextField(
    value: String,
    onValueChange: (value: String) -> Unit,
    label: String,
    modifier: Modifier = Modifier,
    isError: Boolean = false,
    placeholder: @Composable (() -> Unit)? = null,
    leadingIcon: @Composable (() -> Unit)? = null,
    trailingIcon: @Composable (() -> Unit)? = null,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    keyboardActions: KeyboardActions = KeyboardActions()
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        shape = MaterialTheme.shapes.medium,
        label = { TextFieldLabel(label) },
        singleLine = true,
        colors = TextFieldDefaults.outlinedTextFieldColors(
            backgroundColor = MaterialTheme.colors.surface,
            //TODO
        ),
        isError = isError,
        placeholder = placeholder,
        leadingIcon = leadingIcon,
        trailingIcon = trailingIcon,
        visualTransformation = visualTransformation,
        keyboardOptions = keyboardOptions,
        keyboardActions = keyboardActions,
        modifier = modifier
    )
}

@Composable
fun HelperText(
    text: String,
    modifier: Modifier = Modifier,
    isError: Boolean = false
) {
    Text(
        text = text,
        style = MaterialTheme.typography.caption,
        fontSize = 10.sp,
        color = (if (isError) MaterialTheme.colors.error else MaterialTheme.colors.onBackground).copy(
            alpha = 0.72F
        ),
        modifier = modifier
            .padding(top = 2.dp)
    )
}

@Composable
fun TextFieldLabel(label: String) {
    Text(text = label)
}
