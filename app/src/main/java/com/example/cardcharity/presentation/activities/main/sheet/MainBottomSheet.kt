package com.example.cardcharity.presentation.activities.main.sheet

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.annotation.ExperimentalCoilApi
import coil.compose.ImagePainter
import coil.compose.rememberImagePainter
import coil.request.CachePolicy
import coil.request.Parameters
import coil.transform.CircleCropTransformation
import com.example.cardcharity.R
import com.example.cardcharity.presentation.theme.PreviewTheme
import com.example.cardcharity.presentation.ui.elements.TopButton
import com.example.cardcharity.presentation.ui.elements.VerticalSpace
import com.example.cardcharity.presentation.ui.elements.previewUser
import com.example.cardcharity.repository.model.Shop
import com.example.cardcharity.repository.model.User
import com.example.cardcharity.utils.checkOrNull
import com.example.cardcharity.utils.extensions.api
import com.example.cardcharity.utils.extensions.getImageUrlCodeImage
import com.example.cardcharity.utils.extensions.getImageUrlLogo
import timber.log.Timber

/*
- label
- qr code margin
- bottom close button
 */

@OptIn(ExperimentalCoilApi::class)
@Composable
fun MainBottomSheet(
    shop: Shop?,
    user: User?,
    onClose: () -> Unit
) {
    checkOrNull(shop != null && user != null) ?: run {
        VerticalSpace(1.dp)
        return
    }

    val context = LocalContext.current
    val apiUrl = context.api.url.toString()

    Surface(color = MaterialTheme.colors.background) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxWidth()
        ) {
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .background(color = MaterialTheme.colors.surface)
                    .fillMaxWidth()
            ) {
                Image(
                    painter = rememberImagePainter(
                        data = shop.getImageUrlLogo(apiUrl),
                        builder = {
                            crossfade(true)
                            transformations(CircleCropTransformation())
                        }
                    ),
                    contentScale = ContentScale.FillBounds,
                    contentDescription = null,
                    modifier = Modifier
                        .padding(start = 16.dp, top = 16.dp, bottom = 16.dp)
                        .size(48.dp)
                )

                Text(
                    text = shop.name,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colors.onSurface,
                    letterSpacing = 0.8.sp,
                    textAlign = TextAlign.Start,
                    modifier = Modifier
                        .padding(horizontal = 16.dp)
                        .weight(1F)
                )

                TopButton(
                    onClick = onClose,
                    painter = painterResource(R.drawable.ic_close_24)
                )
            }

            Card(
                backgroundColor = Color.White,
                elevation = 16.dp,
                shape = MaterialTheme.shapes.medium,
                modifier = Modifier
                    .padding(horizontal = 56.dp)
                    .padding(top = 56.dp, bottom = 56.dp)
                    .aspectRatio(1F)
                    .fillMaxWidth()
            ) {

                Box(modifier = Modifier.fillMaxSize()) {
                    val painter = rememberImagePainter(
                        data = shop.getImageUrlCodeImage(
                            url = apiUrl,
                            uid = user.uid
                        ),
                        builder = {
                            crossfade(true)
                            this.parameters(Parameters())
                            memoryCachePolicy(CachePolicy.DISABLED)
                            diskCachePolicy(CachePolicy.DISABLED)
                        }
                    )

                    if(painter.state is ImagePainter.State.Error) {
                        //TODO
                        Text(
                            text = "Error",
                            color = Color.Black,
                            modifier = Modifier.align(Alignment.Center)
                        )
                    }

                    Image(
                        painter = painter,
                        contentScale = ContentScale.FillBounds,
                        contentDescription = null,
                        modifier = Modifier
                            .padding(all = 8.dp)
                            .fillMaxSize()
                    )
                }
            }

            Text(
                text = stringResource(R.string.show_code),
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colors.onBackground.copy(alpha = 0.72F),
                modifier = Modifier.padding(horizontal = 16.dp)
            )

            VerticalSpace(56.dp)
        }
    }
}


@Preview
@Composable
fun MainBottomSheetPreview() {
    PreviewTheme {
        MainBottomSheet(
            shop = Shop(1, "Shop name"),
            user = previewUser,
            onClose = {}
        )
    }
}
