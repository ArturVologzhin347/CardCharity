package com.example.cardcharity.presentation.ui.elements

import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.ImagePainter
import coil.compose.rememberImagePainter
import coil.transform.CircleCropTransformation
import com.example.cardcharity.presentation.theme.PreviewTheme
import com.example.cardcharity.repository.model.User
import com.example.cardcharity.utils.extensions.abbreviation
import timber.log.Timber

val previewUser = User(
    uid = "",
    email = "test@gmail.com",
    name = "Artur Vologzhin",
    photoUrl = null,
    createdAt = System.currentTimeMillis()
)

@Composable
fun AvatarImage(
    user: User,
    fontSize: TextUnit,
    size: Dp,
    modifier: Modifier = Modifier,
    colorBackground: Color = MaterialTheme.colors.primary,
    colorOnBackground: Color = MaterialTheme.colors.onPrimary
) {
    if (user.photoUrl != null) {
        LoadableAvatarImage(
            photoUrl = user.photoUrl,
            size = size,
            modifier = modifier
        )
    } else {
        WritableAvatarImage(
            user = user,
            fontSize = fontSize,
            size = size,
            modifier = modifier,
            colorBackground = colorBackground,
            colorOnBackground = colorOnBackground
        )
    }
}


@Composable
private fun LoadableAvatarImage(
    photoUrl: Uri,
    size: Dp,
    modifier: Modifier
) {
    Image(
        painter = rememberImagePainter(
            data = photoUrl,
            builder = {
                crossfade(true)
                transformations(CircleCropTransformation())

            }
        ),
        contentScale = ContentScale.FillBounds,
        modifier = modifier.size(size),
        contentDescription = null
    )
}

@Composable
private fun WritableAvatarImage(
    user: User,
    fontSize: TextUnit,
    size: Dp,
    modifier: Modifier,
    colorBackground: Color,
    colorOnBackground: Color
) {
    Box(
        modifier = modifier
            .background(
                color = colorBackground,
                shape = CircleShape
            )
            .size(size)
    ) {
        Text(
            text = user.abbreviation,
            fontSize = fontSize,
            color = colorOnBackground,
            fontWeight = FontWeight.Medium,
            modifier = Modifier
                .align(Alignment.Center)
        )
    }
}


@Preview
@Composable
fun AvatarPreview() {
    PreviewTheme {
        WritableAvatarImage(
            user = previewUser,
            fontSize = 24.sp,
            size = 56.dp,
            modifier = Modifier,
            colorBackground = MaterialTheme.colors.primary,
            colorOnBackground = MaterialTheme.colors.onPrimary
        )
    }
}
