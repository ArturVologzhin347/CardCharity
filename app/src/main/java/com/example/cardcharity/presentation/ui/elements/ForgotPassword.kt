package com.example.cardcharity.presentation.ui.elements

import androidx.compose.foundation.clickable
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.example.cardcharity.R


@Composable
fun ForgotPasswordButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {

    Text(
        text = stringResource(R.string.forgot_your_password),
        style = MaterialTheme.typography.body2,
        fontWeight = FontWeight.Medium,
        color = MaterialTheme.colors.secondary,
        fontSize = 12.sp,
        modifier = modifier
            .clickable(
                enabled = true,
                role = Role.Button,
                onClick = onClick
            )
    )
}