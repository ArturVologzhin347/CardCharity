package com.example.cardcharity.presentation.activities.main.search.list

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberImagePainter
import coil.transform.CircleCropTransformation
import com.example.cardcharity.repository.model.Shop
import com.example.cardcharity.utils.extensions.api
import com.example.cardcharity.utils.extensions.getImageUrlLogo


@Composable
fun ColumnScope.SearchingShopList(
    shops: List<SearchShopItem>,
    onItemClick: (shop: Shop) -> Unit
) {
    val context = LocalContext.current
    val url = context.api.url.toString()

    LazyColumn(
        modifier = Modifier
            .fillMaxWidth()
            .weight(1F)
    ) {

        items(
            count = shops.size,
            key = { index -> shops[index].shop.id },
            itemContent = { index ->
                SearchingShopItem(
                    item = shops[index],
                    onClick = onItemClick,
                    url = url
                )
                Divider()
            }
        )
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun SearchingShopItem(
    item: SearchShopItem,
    onClick: (shop: Shop) -> Unit,
    url: String
) {
    val shop = item.shop

    Card(
        shape = RectangleShape,
        backgroundColor = MaterialTheme.colors.surface,
        onClick = { onClick(item.shop) },
        elevation = 0.dp,
        modifier = Modifier
            .fillMaxWidth()
            .height(68.dp)
    ) {
        Row(
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(horizontal = 16.dp)
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
                text = item.getAnnotatedName(
                    focusedStyle = getFocusedTextStyle(),
                    unfocusedStyle = getUnfocusedTextStyle()
                ),
                modifier = Modifier.padding(start = 18.dp, end = 16.dp)
            )
        }
    }
}

@Composable
private fun getFocusedTextStyle() = SpanStyle(
    fontSize = 18.sp,
    fontWeight = FontWeight.Medium,
    color = MaterialTheme.colors.primary
)

@Composable
private fun getUnfocusedTextStyle() = SpanStyle(
    fontSize = 18.sp,
    fontWeight = FontWeight.Medium,
    color = MaterialTheme.colors.onBackground
)
