package com.example.cardcharity.presentation.ui.elements

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*

import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.example.cardcharity.R
import com.example.cardcharity.presentation.theme.surface8Primary

@Composable
fun ResetPasswordDialog(
    isOpen: MutableState<Boolean>,
    onClick: () -> Unit,
    email: String
) {
    BaseDialog(
        isOpen = isOpen,
        painter = painterResource(R.drawable.ic_send_24),
        label = stringResource(R.string.reset_email_send),
        text = "${stringResource(R.string.mail_was_sent_to)} $email.",
        buttons = arrayOf(
            {
                BaseDialogButton(
                    onClick = onClick,
                    text = stringResource(R.string.ok)
                )
            }
        )
    )
}

@Composable
fun SignOutDialog(
    isOpen: MutableState<Boolean>,
    onSignOut: () -> Unit
) {
    BaseDialog(
        isOpen = isOpen,
        painter = painterResource(R.drawable.ic_logout_24),
        label = stringResource(R.string.sign_out_from_your_account),
        text = stringResource(R.string.sign_out_help),
        buttons = arrayOf(
            {
                BaseDialogButton(
                    onClick = { isOpen.value = false },
                    text = stringResource(R.string.back),
                    color = MaterialTheme.colors.onBackground.copy(alpha = 0.54F)
                )
            },
            {
                BaseDialogButton(
                    onClick = onSignOut,
                    text = stringResource(R.string.sign_out)
                )
            }
        ),
    )
}

@Composable
fun BaseDialog(
    isOpen: MutableState<Boolean>,
    painter: Painter,
    label: String,
    text: String,
    buttons: Array<@Composable RowScope.() -> Unit>
) {
    CustomDialog(isOpen = isOpen) {
        Card(
            shape = RoundedCornerShape(10.dp),
            elevation = 8.dp,
            backgroundColor = MaterialTheme.colors.surface,
            modifier = Modifier.padding(horizontal = 16.dp)
        ) {
            Column(
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.fillMaxWidth()
            ) {
                Image(
                    painter = painter,
                    colorFilter = ColorFilter.tint(MaterialTheme.colors.primary),
                    contentScale = ContentScale.FillBounds,
                    contentDescription = null,
                    modifier = Modifier
                        .padding(vertical = 24.dp)
                        .size(72.dp)
                )

                Text(
                    text = label,
                    fontSize = 28.sp,
                    fontWeight = FontWeight.Medium,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .padding(horizontal = 16.dp)
                )

                Text(
                    text = text,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Normal,
                    lineHeight = 20.sp,
                    overflow = TextOverflow.Ellipsis,
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .padding(horizontal = 16.dp)
                        .padding(bottom = 24.dp, top = 16.dp)
                )

                Row(
                    modifier = Modifier
                        .background(MaterialTheme.colors.surface8Primary)
                        .fillMaxWidth()
                        .height(56.dp)
                ) { buttons.forEach { button -> button() } }
            }
        }
    }
}

@Composable
private fun RowScope.BaseDialogButton(
    onClick: () -> Unit,
    text: String,
    color: Color = MaterialTheme.colors.primary
) {
    TextButton(
        onClick = onClick,
        shape = RectangleShape,
        modifier = Modifier
            .fillMaxHeight()
            .weight(1F)
    ) {
        Text(
            text = text,
            maxLines = 1,
            style = MaterialTheme.typography.button,
            color = color
        )
    }
}


@Composable
fun CustomDialog(
    isOpen: MutableState<Boolean>,
    content: @Composable () -> Unit
) {
    if (isOpen.value) {
        Dialog(
            onDismissRequest = { isOpen.value = false },
            content = content
        )
    }
}
