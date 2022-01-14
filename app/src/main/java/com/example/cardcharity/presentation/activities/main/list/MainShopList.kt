package com.example.cardcharity.presentation.activities.main.list

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberImagePainter
import coil.transform.CircleCropTransformation
import com.example.cardcharity.domain.shop.ShopListModel
import com.example.cardcharity.presentation.theme.PreviewTheme
import com.example.cardcharity.presentation.ui.elements.VerticalSpace
import com.example.cardcharity.repository.model.Shop
import com.example.cardcharity.utils.extensions.api
import com.example.cardcharity.utils.extensions.getImageUrlLogo

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun MainShopList(
    shops: List<ShopListModel>,
    onItemClick: (shop: Shop) -> Unit
) {
    val context = LocalContext.current
    val url = context.api.url

    LazyColumn(modifier = Modifier.fillMaxSize()) {
        shops.forEachIndexed() { index, shop ->
            when (shop) {
                is ShopListModel.ShopModel -> {
                    item(key = shop.id) {
                        MainShopItem(
                            model = shop,
                            url = url.toString(),
                            onItemClick = onItemClick
                        )

                        //Divider if next item is label
                        shops.getOrNull(index + 1).let {
                            if (it is ShopListModel.LabelModel) {
                                Divider(modifier = Modifier.padding(start = 56.dp))
                            }
                        }
                    }
                }

                is ShopListModel.LabelModel -> {
                    stickyHeader(key = shop.id) {
                        LabelShopItem(shop)
                    }
                }
            }
        }
        item { VerticalSpace(64.dp) }
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun MainShopItem(
    model: ShopListModel.ShopModel,
    url: String,
    onItemClick: (shop: Shop) -> Unit
) {
    val shop = model.shop

    Card(
        onClick = { onItemClick(shop) },
        backgroundColor = MaterialTheme.colors.surface,
        elevation = 0.dp,
        shape = RectangleShape,
        modifier = Modifier
            .fillMaxWidth()
            .height(64.dp)

    ) {
        Row(
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .padding(
                    start = 56.dp,
                    end = 16.dp
                )
        ) {
            Image(
                painter = rememberImagePainter(
                    data = shop.getImageUrlLogo(url),
                    builder = {
                        crossfade(true)
                        transformations(CircleCropTransformation())
                    }
                ),
                contentDescription = null,
                modifier = Modifier.size(48.dp)
            )

            Text(
                text = shop.name,
                fontSize = 18.sp,
                fontWeight = FontWeight(450),
                overflow = TextOverflow.Ellipsis,
                maxLines = 1,
                color = MaterialTheme.colors.onSurface,
                modifier = Modifier.padding(horizontal = 16.dp)
            )
        }
    }
}

@Composable
fun LabelShopItem(model: ShopListModel.LabelModel) {
    Box(
        modifier = Modifier
            .width(56.dp)
            .height(64.dp)
    ) {
        Text(
            text = model.label,
            color = MaterialTheme.colors.primary,
            fontSize = 28.sp,
            fontWeight = FontWeight.Medium,
            textAlign = TextAlign.Center,
            modifier = Modifier.align(Alignment.Center)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun ShopItemPreview() {
    PreviewTheme {
        MainShopItem(
            model = ShopListModel.shop(
                Shop(
                    id = 1,
                    name = "Red & White"
                )
            ),
            onItemClick = {},
            url = "1234567890"
        )
    }
}
