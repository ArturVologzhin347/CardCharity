package com.example.cardcharity.presentation.activities.main.search

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.cardcharity.R
import com.example.cardcharity.presentation.activities.main.Fail
import com.example.cardcharity.presentation.activities.main.MainFailNoItems
import com.example.cardcharity.presentation.activities.main.MainFailNoNetwork
import com.example.cardcharity.presentation.activities.main.MainFailUnknown
import com.example.cardcharity.presentation.activities.main.search.list.SearchingShopList
import com.example.cardcharity.presentation.activities.main.sheet.MainBottomSheet
import com.example.cardcharity.presentation.theme.PreviewTheme
import com.example.cardcharity.presentation.ui.elements.TopButton
import com.example.cardcharity.presentation.ui.elements.VerticalSpace
import com.example.cardcharity.presentation.ui.elements.previewUser
import com.example.cardcharity.repository.model.Shop
import com.example.cardcharity.repository.model.User
import com.example.cardcharity.utils.extensions.getStatusBarHeight
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


@OptIn(ExperimentalMaterialApi::class)
@ExperimentalComposeUiApi
@Composable
fun SearchScreen(
    reduce: (event: SearchEvent) -> Unit,
    viewState: SearchViewState,
    user: User?
) {

    val scope = rememberCoroutineScope()
    val sheetState = rememberModalBottomSheetState(ModalBottomSheetValue.Hidden)

    val keyboardController = LocalSoftwareKeyboardController.current
    val focusManager = LocalFocusManager.current
    val focusRequester = remember { FocusRequester() }

    var searching by rememberSaveable { mutableStateOf("") }
    var selectedShop by remember { mutableStateOf<Shop?>(null) }

    Surface(
        color = MaterialTheme.colors.surface,
        modifier = Modifier.fillMaxSize()
    ) {
        ModalBottomSheetLayout(
            sheetState = sheetState,
            sheetShape = RectangleShape,
            sheetElevation = 16.dp,
            scrimColor = Color.Black.copy(alpha = 0.42F),
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
        ) {
            Column(modifier = Modifier.fillMaxSize()) {
                Row(
                    horizontalArrangement = Arrangement.Start,
                    verticalAlignment = Alignment.Top,
                    modifier = Modifier
                        .background(MaterialTheme.colors.background)
                        .fillMaxWidth()
                ) {
                    CloseButton(
                        onClick = {
                            reduce(finish())
                        }
                    )

                    SearchField(
                        search = searching,
                        onSearchChange = {
                            searching = it
                            reduce(search(it.trim()))
                        },
                        onSearch = {
                            keyboardController?.hide()
                            focusManager.clearFocus()
                        },
                        modifier = Modifier
                            .padding(start = 12.dp)
                            .weight(1F)
                            .focusRequester(focusRequester)
                    )
                }

                val refresh = { reduce(refresh()) }

                when (viewState) {
                    is Success -> SearchingShopList(
                        shops = viewState.shops,
                        onItemClick = { shop ->
                            scope.launch {
                                selectedShop = shop
                                focusManager.clearFocus()
                                keyboardController?.let {
                                    it.hide()
                                    delay(200)
                                }
                                sheetState.animateTo(ModalBottomSheetValue.Expanded)
                            }
                        }
                    )

                    NoData -> MainFailNoItems(refresh)
                    NoNetwork -> MainFailNoNetwork(refresh)
                    Unknown -> MainFailUnknown(refresh)
                    Load -> SearchLoad()
                    NotFound -> SearchNotFound(searching)
                    LoadData -> {} //No view state
                }
            }
        }

    }

    LaunchedEffect(Unit) {
        focusRequester.requestFocus()
    }
}


@Composable
fun SearchNotFound(searching: String) {
    Fail(
        painter = painterResource(R.drawable.ic_search_24),
        label = stringResource(R.string.not_found),
        text = "${stringResource(R.string.the_shop_with_name)} " +
                "\"${searching.trim()}\" ${stringResource(R.string.was_not_found)}",
        action = null
    )
}

@Composable
fun ColumnScope.SearchLoad() {
    CircularProgressIndicator(
        modifier = Modifier
            .padding(top = 56.dp)
            .align(Alignment.CenterHorizontally)
    )
}

@Composable
fun SearchField(
    search: String,
    onSearchChange: (searching: String) -> Unit,
    onSearch: (search: String) -> Unit,
    modifier: Modifier = Modifier
) {
    TextField(
        value = search,
        onValueChange = onSearchChange,
        colors = TextFieldDefaults.textFieldColors(
            backgroundColor = MaterialTheme.colors.background,
            focusedIndicatorColor = Color.Unspecified,
            unfocusedIndicatorColor = Color.Unspecified
        ),
        textStyle = LocalTextStyle.current.copy(
            fontSize = 18.sp
        ),
        placeholder = {
            Text(
                text = stringResource(R.string.search),
                fontSize = 18.sp
            )
        },
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Text,
            imeAction = ImeAction.Search
        ),
        keyboardActions = KeyboardActions(
            onSearch = { onSearch(search) }
        ),
        singleLine = true,
        modifier = modifier
    )
}

@Composable
fun CloseButton(onClick: () -> Unit) {
    TopButton(
        onClick = onClick,
        painter = painterResource(R.drawable.ic_close_24)
    )
}

@OptIn(ExperimentalComposeUiApi::class)
@Preview(showSystemUi = true, showBackground = true)
@Composable
fun SearchScreenPreview() {
    PreviewTheme {
        SearchScreen(
            reduce = {},
            viewState = loadData(),
            user = previewUser
        )
    }
}
