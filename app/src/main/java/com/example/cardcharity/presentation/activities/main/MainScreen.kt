package com.example.cardcharity.presentation.activities.main

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.cardcharity.R
import com.example.cardcharity.presentation.activities.main.list.MainShopList
import com.example.cardcharity.presentation.activities.main.sheet.MainBottomSheet
import com.example.cardcharity.presentation.theme.PreviewTheme
import com.example.cardcharity.presentation.ui.elements.*
import com.example.cardcharity.repository.model.Shop
import com.example.cardcharity.repository.model.User
import com.example.cardcharity.utils.extensions.getStatusBarHeight
import com.example.cardcharity.utils.extensions.noRippleClickable
import kotlinx.coroutines.launch
import timber.log.Timber

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun MainScreen(
    reduce: (event: MainEvent) -> Unit,
    viewState: MainViewState,
    user: User?
) {
    val scope = rememberCoroutineScope()
    val sheetState = rememberModalBottomSheetState(ModalBottomSheetValue.Hidden)
    var selectedShop by remember { mutableStateOf<Shop?>(null) }

    LaunchedEffect(sheetState.isVisible) {
        reduce(highlight(sheetState.isVisible))
    }

    ModalBottomSheetLayout(
        sheetState = sheetState,
        sheetShape = RectangleShape,
        sheetElevation = 16.dp,
        sheetContent = {
            MainBottomSheet(
                shop = selectedShop,
                user = user,
                onClose = {
                    scope.launch {
                        sheetState.hide()
                    }
                }
            )

        },
        scrimColor = Color.Black.copy(alpha = 0.42F),
        content = {
            MainContent(
                reduce = reduce,
                user = user,
                viewState = viewState,
                openSheet = { shop ->
                    scope.launch {
                        selectedShop = shop
                        sheetState.animateTo(ModalBottomSheetValue.Expanded)
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
    user: User?,
    reduce: (event: MainEvent) -> Unit,
    openSheet: (shop: Shop) -> Unit
) {
    val backdropState = rememberBackdropScaffoldState(initialValue = BackdropValue.Revealed)


    BackdropScaffold(
        scaffoldState = backdropState,
        peekHeight = 56.dp,
        persistentAppBar = true,
        frontLayerScrimColor = Color.Unspecified,
        appBar = {},
        frontLayerElevation = 0.dp,
        backLayerBackgroundColor = MaterialTheme.colors.background,
        frontLayerBackgroundColor = MaterialTheme.colors.surface,
        frontLayerShape = RectangleShape,

        backLayerContent = {
            MainBackLayerContent(
                user = user,
                reduce = reduce
            )
        },

        frontLayerContent = {
            MainFrontLayerContent(
                viewState = viewState,
                reduce = reduce,
                onItemClick = { shop ->
                    openSheet(shop)
                }
            )
        }
    )
}

@Composable
fun MainBackLayerContent(
    user: User?,
    reduce: (event: MainEvent) -> Unit
) {
    Column(modifier = Modifier.fillMaxWidth()) {
        MainTopBar(
            user = user,
            onAvatarClick = { reduce(settings()) },
            onSearchClick = { reduce(search()) }
        )
        VerticalSpace(8.dp)

        SelectShopLabel()

    }
}


@Composable
fun SelectShopLabel() {
    Text(
        text = stringResource(R.string.select_shop),
        fontSize = 32.sp,
        fontWeight = FontWeight.Light,
        color = MaterialTheme.colors.onBackground,
        modifier = Modifier
            .padding(horizontal = 16.dp)
            .padding(bottom = 16.dp, top = 16.dp)
    )
}

@Composable
fun MainTopBar(
    user: User?,
    onAvatarClick: () -> Unit,
    onSearchClick: () -> Unit
) {
    user ?: return

    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .height(56.dp)
    ) {
        AvatarImage(
            user = user,
            fontSize = 14.sp,
            size = 32.dp,
            modifier = Modifier
                .padding(start = 16.dp)
                .noRippleClickable(onClick = onAvatarClick)
        )

        TopButton(
            onClick = onSearchClick,
            painter = painterResource(R.drawable.ic_search_24)
        )
    }
}

@Composable
fun MainFrontLayerContent(
    viewState: MainViewState,
    reduce: (event: MainEvent) -> Unit,
    onItemClick: (shop: Shop) -> Unit
) {
    val refresh = { reduce(refresh()) }

    when (viewState) {
        is Success -> {
            MainShopList(
                shops = viewState.shops,
                onItemClick = onItemClick
            )
        }

        Load -> MainLoad()
        NoNetwork -> MainFailNoNetwork(refresh)
        NoItems -> MainFailNoItems(refresh)
        Unknown -> MainFailUnknown(refresh)
    }
}

@Composable
fun MainFailNoNetwork(onRefresh: () -> Unit) {
    Fail(
        painter = painterResource(R.drawable.ic_network_off_24),
        label = stringResource(R.string.error_network),
        text = stringResource(R.string.error_network_message),
        action = onRefresh to stringResource(R.string.refresh)
    )
}

@Composable
fun MainFailNoItems(onRefresh: () -> Unit) {
    Fail(
        painter = painterResource(R.drawable.ic_warning_24),
        label = stringResource(R.string.error_server),
        text = stringResource(R.string.error_server_message),
        action = onRefresh to stringResource(R.string.refresh)
    )
}

@Composable
fun MainFailUnknown(
    onRefresh: () -> Unit
) {
    Fail(
        painter = painterResource(R.drawable.ic_warning_24),
        label = stringResource(R.string.oops),
        text = stringResource(R.string.error_unexpected_message),
        action = onRefresh to stringResource(R.string.refresh)
    )
}

@Composable
fun Fail(
    painter: Painter,
    label: String,
    text: String,
    modifier: Modifier = Modifier,
    action: Pair<() -> Unit, String>?
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier.fillMaxSize()
    ) {

        Image(
            painter = painter,
            colorFilter = ColorFilter.tint(MaterialTheme.colors.primary),
            contentDescription = null,
            modifier = Modifier
                .padding(top = 24.dp, bottom = 16.dp)
                .padding(horizontal = 16.dp)
                .size(128.dp)
        )

        Text(
            text = label,
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            maxLines = 2,
            overflow = TextOverflow.Ellipsis,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .padding(horizontal = 48.dp)
                .padding(bottom = 8.dp)
        )

        Text(
            text = text,
            fontSize = 16.sp,
            fontWeight = FontWeight(450),
            lineHeight = 24.sp,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .padding(horizontal = 48.dp)
                .padding(bottom = 16.dp)
        )

        action?.let {
            TextButton(
                onClick = it.first,
                modifier = Modifier.padding(bottom = 24.dp)
            ) {
                ButtonText(
                    text = it.second,
                    color = MaterialTheme.colors.primary
                )
            }
        }
    }
}

@Composable
fun MainLoad() {
    Box(modifier = Modifier.fillMaxWidth()) {
        LinearProgressIndicator(
            color = MaterialTheme.colors.primary,
            modifier = Modifier
                .fillMaxWidth()
        )
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun MainScreenPreview() {
    PreviewTheme {
        MainScreen(
            reduce = {},
            user = previewUser,
            viewState = load()
        )
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun FailPreview() {
    PreviewTheme {
        MainFailUnknown {

        }
    }
}