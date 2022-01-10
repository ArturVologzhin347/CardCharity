package com.example.cardcharity.presentation.activities.settings

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.cardcharity.BuildConfig
import com.example.cardcharity.R
import com.example.cardcharity.presentation.theme.PreviewTheme
import com.example.cardcharity.presentation.ui.elements.*
import com.example.cardcharity.repository.model.User
import com.example.cardcharity.utils.extensions.nullIfEmpty
import com.example.cardcharity.utils.ifNull

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun SettingsScreen(
    reduce: (event: SettingsEvent) -> Unit,
    user: User?,
    viewState: SettingsViewState
) {
    user.ifNull { return }

    val backdropState = rememberBackdropScaffoldState(initialValue = BackdropValue.Revealed)

    
    BackdropScaffold(
        scaffoldState = backdropState,
        appBar = {},
        peekHeight = 56.dp,
        frontLayerScrimColor = Color.Unspecified,
        frontLayerElevation = 0.dp,

        backLayerBackgroundColor = MaterialTheme.colors.background,
        frontLayerBackgroundColor = MaterialTheme.colors.surface,
        frontLayerShape = RectangleShape,
        backLayerContent = {
            SettingsBackLayerContent(
                reduce = reduce,
                user = user,
                viewState = viewState
            )
        },
        frontLayerContent = {
            SettingsFrontLayerContent(
                reduce = reduce,
                viewState = viewState
            )
        }
    )
}

@Composable
fun SettingsFrontLayerContent(
    reduce: (event: SettingsEvent) -> Unit,
    viewState: SettingsViewState
) {
    val nightMode = isSystemInDarkTheme()
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        VerticalSpace(16.dp)
        SettingItemsContainer(
            header = {
                SettingItemContainerLabel(
                    label = stringResource(R.string.appearance)
                )
            },
            items = listOf(
                {
                    SettingNightModeItem(
                        checked = viewState.nightMode,
                        enabled = viewState.nightModeEnabled,
                        onCheckedChange = { checked ->
                            reduce(SettingsEvent.nightMode(checked))
                        })
                },
                {
                    SettingSyncWithSystemTheme(
                        checked = viewState.syncWithSystemTheme,
                        enabled = viewState.syncWithSystemThemeEnabled,
                        onCheckedChange = { checked ->
                            reduce(
                                SettingsEvent.syncWithSystemTheme(
                                    enabled = checked,
                                    nightMode = nightMode
                                )
                            )
                        }
                    )
                }
            )
        )

        VerticalSpace(16.dp)

        SettingItemsContainer(
            header = {
                SettingItemContainerLabel(stringResource(R.string.settings))
            },
            items = listOf(
                {
                    SettingHighlightCode(
                        checked = viewState.highlightCode,
                        onCheckedChange = { checked ->
                            reduce(SettingsEvent.highlightCode(checked))
                        }
                    )
                },
                {
                    SettingResetPassword(
                        onClick = {
                            reduce(SettingsEvent.resetPassword())
                        }
                    )
                },
            )
        )

        VerticalSpace(16.dp)

        SettingItemsContainer(
            header = {},
            items = listOf {
                SettingAboutApp(onClick = { reduce(SettingsEvent.aboutApp()) })
            }
        )

        AppVersion()
    }
}

@Composable
fun AppVersion() {
    val appName = stringResource(R.string.app_name)
    val version = BuildConfig.VERSION_CODE
    val versionName = BuildConfig.VERSION_NAME

    Text(
        text = "$appName for Android v$versionName ($version)",
        fontSize = 12.sp,
        fontWeight = FontWeight.Normal,
        color = MaterialTheme.colors.onSurface.copy(alpha = 0.72F),
        modifier = Modifier.padding(all = 16.dp)
    )
}

@Composable
fun SettingResetPassword(
    onClick: () -> Unit
) {
    SettingItemNavigation(
        onClick = onClick,
        painter = painterResource(R.drawable.ic_lock_24),
        label = stringResource(R.string.reset_password)
    )
}

@Composable
fun SettingAboutApp(
    onClick: () -> Unit
) {
    SettingItemNavigation(
        onClick = onClick,
        painter = painterResource(R.drawable.ic_info_24),
        label = stringResource(R.string.about_app)
    )
}

@Composable
fun SettingHighlightCode(
    checked: Boolean,
    onCheckedChange: (checked: Boolean) -> Unit
) {
    SettingItemSwitch(
        painter = painterResource(R.drawable.ic_sun_24),
        label = stringResource(R.string.highlight_code),
        checked = checked,
        onCheckedChange = onCheckedChange
    )
}

@Composable
fun SettingSyncWithSystemTheme(
    checked: Boolean,
    enabled: Boolean,
    onCheckedChange: (checked: Boolean) -> Unit
) {
    SettingItemSwitch(
        painter = painterResource(R.drawable.ic_android_24),
        label = stringResource(R.string.sync_with_os_theme),
        enabled = enabled,
        checked = checked,
        onCheckedChange = onCheckedChange
    )
}


@Composable
fun SettingNightModeItem(
    checked: Boolean,
    enabled: Boolean,
    onCheckedChange: (checked: Boolean) -> Unit
) {
    SettingItem(
        painter = painterResource(R.drawable.ic_night_mode_24),
        label = stringResource(R.string.night_mode)
    ) {
        Row(
            horizontalArrangement = Arrangement.End,
            verticalAlignment = Alignment.CenterVertically
        ) {
            if (!enabled) {
                Text(
                    text = stringResource(R.string.synced),
                    fontSize = 12.sp,
                    overflow = TextOverflow.Ellipsis,
                    color = MaterialTheme.colors.onBackground.copy(alpha = 0.72F),
                    modifier = Modifier
                        .padding(end = 16.dp)
                )
            }

            Switch(
                enabled = enabled,
                checked = checked,
                onCheckedChange = onCheckedChange
            )
        }
    }
}

@Composable
fun SettingItemContainerLabel(label: String) {
    Text(
        text = label,
        color = MaterialTheme.colors.primary,
        fontWeight = FontWeight.Medium,
        fontSize = 14.sp,
        modifier = Modifier.padding(start = 16.dp, top = 16.dp)
    )
}

@Composable
fun SettingItemsContainer(
    header: @Composable ColumnScope.() -> Unit,
    items: List<@Composable ColumnScope.() -> Unit>
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = 0.dp,
        backgroundColor = MaterialTheme.colors.background,
        shape = RectangleShape
    ) {
        Column {
            header()

            items.forEachIndexed { index, function ->
                function()
                if (index != items.lastIndex) {
                    Divider(modifier = Modifier.padding(start = 56.dp))
                }
            }
        }
    }
}

@Composable
fun SettingItemSwitch(
    checked: Boolean,
    onCheckedChange: (checked: Boolean) -> Unit,
    painter: Painter,
    label: String,
    enabled: Boolean = true
) {
    SettingItem(
        painter = painter,
        label = label
    ) {
        Switch(
            enabled = enabled,
            checked = checked,
            onCheckedChange = onCheckedChange
        )
    }
}

@Composable
fun SettingItemNavigation(
    onClick: () -> Unit,
    painter: Painter,
    label: String
) {
    ClickableSettingItem(
        onClick = onClick,
        painter = painter,
        label = label,
        content = {
            Icon(
                painter = painterResource(R.drawable.ic_arrow_right_24),
                contentDescription = null
            )
        }
    )
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun ClickableSettingItem(
    onClick: () -> Unit,
    painter: Painter,
    label: String,
    content: @Composable BoxScope.() -> Unit
) {
    Card(
        elevation = 0.dp,
        shape = RectangleShape,
        backgroundColor = MaterialTheme.colors.background,
        onClick = onClick,
        modifier = Modifier
            .fillMaxWidth()
            .height(56.dp)
    ) {
        SettingItemContent(
            painter = painter,
            label = label,
            content = content
        )
    }
}

@Composable
fun SettingItem(
    painter: Painter,
    label: String,
    content: @Composable BoxScope.() -> Unit
) {
    Card(
        elevation = 0.dp,
        shape = RectangleShape,
        backgroundColor = MaterialTheme.colors.background,
        modifier = Modifier
            .fillMaxWidth()
            .height(56.dp)

    ) {
        SettingItemContent(
            painter = painter,
            label = label,
            content = content
        )
    }
}

@Composable
fun SettingItemContent(
    painter: Painter,
    label: String,
    content: @Composable BoxScope.() -> Unit
) {
    Row(
        horizontalArrangement = Arrangement.Start,
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .padding(horizontal = 16.dp)
            .fillMaxSize()
    ) {
        SettingIcon(painter = painter)
        SettingItemLabel(label = label)
        Box(modifier = Modifier.weight(1F)) {
            Box(
                modifier = Modifier.align(Alignment.CenterEnd),
                content = content
            )
        }
    }
}

@Composable
fun SettingItemLabel(label: String) {
    Text(
        text = label,
        fontSize = 16.sp,
        fontWeight = FontWeight(450),
        overflow = TextOverflow.Ellipsis,
        maxLines = 1,
        modifier = Modifier.padding(horizontal = 16.dp)
    )
}

@Composable
fun SettingIcon(painter: Painter) {
    Box(
        modifier = Modifier.background(
            color = MaterialTheme.colors.primary,
            shape = RoundedCornerShape(6.dp)
        )
    ) {
        Icon(
            painter = painter,
            tint = MaterialTheme.colors.onPrimary,
            contentDescription = null,
            modifier = Modifier
                .padding(all = 4.dp)
                .size(20.dp)
                .align(Alignment.Center)
        )
    }
}

@Composable
fun SettingsBackLayerContent(
    reduce: (event: SettingsEvent) -> Unit,
    user: User?,
    viewState: SettingsViewState
) {
    Column(
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxWidth()
    ) {
        TopAppBar(reduce = reduce)
        user ?: return

        Row(
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .fillMaxWidth()
        ) {
            AvatarImage(
                user = user,
                fontSize = 28.sp,
                size = 56.dp,
                modifier = Modifier.padding(vertical = 16.dp)
            )

            Column(
                horizontalAlignment = Alignment.Start,
                verticalArrangement = Arrangement.Center,
                modifier = Modifier
                    .padding(horizontal = 16.dp)
                    .weight(1F)
            ) {
                Text(
                    text = user.name.nullIfEmpty() ?: user.email,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colors.onBackground,
                    maxLines = 1,
                    textAlign = TextAlign.Center,
                    overflow = TextOverflow.Ellipsis
                )

                if (user.name.nullIfEmpty() != null) {
                    Text(
                        text = user.email,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Normal,
                        color = MaterialTheme.colors.onBackground.copy(alpha = 0.84F),
                        maxLines = 1,
                        textAlign = TextAlign.Center,
                        overflow = TextOverflow.Ellipsis,
                        modifier = Modifier.padding(top = 4.dp)
                    )
                }
            }
        }
    }
}

@Composable
fun TopAppBar(reduce: (event: SettingsEvent) -> Unit) {
    val showSignOutDialog = remember { mutableStateOf(false) }

    SignOutDialog(
        isOpen = showSignOutDialog,
        onSignOut = {
            showSignOutDialog.value = false
            reduce(SettingsEvent.signOut())
        }
    )

    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.fillMaxWidth()
    ) {
        BackButton(onClick = { reduce(SettingsEvent.back()) })
        TopButton(
            onClick = { showSignOutDialog.value = true },
            painter = painterResource(R.drawable.ic_logout_24)
        )
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun SettingsScreenPreview() {
    PreviewTheme {
        SettingsScreen(
            reduce = {},
            user = previewUser,
            viewState = SettingsViewState.default(
                nightMode = false,
                syncWithSystemTheme = true,
                highlightCode = true
            )
        )
    }
}
