package com.example.cardcharity.presentation.activities.main.list

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.cardcharity.domain.shop.ShopListModel
import com.example.cardcharity.repository.model.Shop

@Composable
fun MainShopList(
    shops: List<ShopListModel>,
    onItemClick: (shop: Shop) -> Unit
) {
    LazyColumn(modifier = Modifier.fillMaxSize()) {
        items(
            count = shops.size,
            key = { index -> shops[index].id },
            itemContent = { index ->
                shops[index].let { model ->
                    when (model) {
                        is ShopListModel.ShopModel -> MainShopItem(
                            model = model,
                            onItemClick = onItemClick
                        )
                        is ShopListModel.LabelModel -> LabelShopItem(model)
                    }
                }
            }
        )
    }
}

@Composable
fun MainShopItem(
    model: ShopListModel.ShopModel,
    onItemClick: (shop: Shop) -> Unit
) {

}

fun LabelShopItem(model: ShopListModel.LabelModel) {

}
