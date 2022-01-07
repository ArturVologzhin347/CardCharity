package com.example.cardcharity.presentation.activities.main

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.cardcharity.presentation.activities.main.list.MainShopList
import com.example.cardcharity.presentation.activities.main.sheet.MainBottomSheet
import com.example.cardcharity.presentation.theme.PreviewTheme
import com.example.cardcharity.presentation.theme.primaryContainer
import com.example.cardcharity.repository.model.Shop
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun MainScreen(
    reduce: (event: MainEvent) -> Unit,
    viewState: MainViewState
) {

    val scope = rememberCoroutineScope()
    val sheetState = rememberModalBottomSheetState(ModalBottomSheetValue.Hidden)
    val selectedShop = rememberSaveable { mutableStateOf<Shop?>(null) }

    ModalBottomSheetLayout(
        sheetState = sheetState,
        sheetContent = { MainBottomSheet(selectedShop.value) },
        content = {
            MainContent(
                reduce = reduce,
                viewState = viewState,
                openSheet = { shop ->
                    scope.launch {
                        selectedShop.value = shop
                        sheetState.show()
                    }
                },
                closeSheet = {
                    scope.launch {
                        sheetState.hide()
                    }
                }
            )
        },
        modifier = Modifier.fillMaxSize()
    )
}


@OptIn(ExperimentalMaterialApi::class)
@Composable
fun MainContent(
    viewState: MainViewState,
    reduce: (event: MainEvent) -> Unit,
    openSheet: (shop: Shop) -> Unit,
    closeSheet: () -> Unit
) {
    val backdropState = rememberBackdropScaffoldState(initialValue = BackdropValue.Revealed)
    BackdropScaffold(
        scaffoldState = backdropState,
        peekHeight = 0.dp,
        persistentAppBar = false,
        appBar = {},
        frontLayerElevation = 0.dp,
        backLayerBackgroundColor = MaterialTheme.colors.background,
        frontLayerBackgroundColor = MaterialTheme.colors.surface,
        frontLayerShape = MaterialTheme.shapes.medium.copy(
            bottomStart = CornerSize(0),
            bottomEnd = CornerSize(0)
        ),
        backLayerContent = {
            Box(modifier = Modifier.height(400.dp)) {}
        },
        frontLayerContent = {
            when (viewState) {
                MainViewState.Fail.NoItems -> TODO()
                MainViewState.Fail.NoNetworkConnection -> TODO()
                MainViewState.Fail.NotFound -> TODO()
                MainViewState.Load -> MainLoading()
                is MainViewState.Success -> MainShopList(
                    shops = viewState.shops,
                    onItemClick = { shop ->
                        openSheet(shop)
                    }
                )
            }
        }
    )
}

@Composable
fun MainLoading() {
    Box(modifier = Modifier.fillMaxWidth()) {
        CircularProgressIndicator(
            color = MaterialTheme.colors.primary,
            modifier = Modifier
                .padding(top = 56.dp)
                .align(Alignment.Center)
        )
    }
}


@Preview(showBackground = true, showSystemUi = true)
@Composable
fun MainScreenPreview() {
    PreviewTheme {
        MainScreen(
            reduce = {},
            viewState = MainViewState.load()
        )
    }
}