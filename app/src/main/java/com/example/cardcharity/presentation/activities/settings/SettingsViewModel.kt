package com.example.cardcharity.presentation.activities.settings

import android.app.Application
import android.content.Intent
import com.example.cardcharity.di.Dagger2
import com.example.cardcharity.presentation.activities.splash.SplashActivity
import com.example.cardcharity.presentation.base.BaseViewModel
import com.example.cardcharity.presentation.theme.DarkThemeManager
import com.example.cardcharity.repository.preferences.Preferences
import com.example.cardcharity.utils.checkOrNull
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import timber.log.Timber
import javax.inject.Inject

class SettingsViewModel(application: Application) : BaseViewModel(application) {

    @Inject
    lateinit var preferences: Preferences

    @Inject
    lateinit var themeManager: DarkThemeManager

    override fun inject(dagger: Dagger2) {
        super.inject(dagger)
        dagger.repositoryComponent.inject(this)
    }

    private val nightMode: Boolean
        get() = preferences.theme == DarkThemeManager.Theme.NIGHT

    private val syncWithSystemTheme: Boolean
        get() = preferences.theme == DarkThemeManager.Theme.AUTO

    private var highlightCode: Boolean
        get() = preferences.highlightCode
        set(value) {
            preferences.highlightCode = value
        }

    private val _viewState = MutableStateFlow(getCurrentViewState())
    val viewState = _viewState.asStateFlow()

    var state: SettingsViewState
        get() = viewState.value
        private set(value) {
            _viewState.value = value
            Timber.i("Current state: $state")
        }


    fun reduceEvent(event: SettingsEvent) {
        when (event) {
            is SettingsEvent.NightMode -> nightModeEvent(event.enabled)
            is SettingsEvent.SyncWithSystemTheme -> syncWithSystemThemeEvent(event)
            is SettingsEvent.HighlightCode -> highlightCodeEvent(event.enabled)
            SettingsEvent.SignOut -> signOutEvent()
            else -> throw NotImplementedError("Event $event is not implemented in $TAG")
        }
    }

    private fun nightModeEvent(enabled: Boolean) {
        checkOrNull(state.nightModeEnabled) ?: return
        with(themeManager) {
            setNewTheme(getCurrentTheme(nightMode = enabled))
            refreshState()
        }
    }

    private fun syncWithSystemThemeEvent(event: SettingsEvent.SyncWithSystemTheme) {
        checkOrNull(state.syncWithSystemThemeEnabled) ?: return
        with(themeManager) {
            setSyncWithSystemTheme(
                localInNightMode = event.nightMode,
                synchronized = event.enabled
            )
            refreshState()
        }
    }

    private fun highlightCodeEvent(enabled: Boolean) {
        highlightCode = enabled
        refreshState()
    }


    private fun signOutEvent() {
        authorization.signOut()
        Intent(context, SplashActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            context.startActivity(this)
        }
    }


    private fun getCurrentViewState(): SettingsViewState {
        return SettingsViewState.default(
            nightMode = nightMode,
            syncWithSystemTheme = syncWithSystemTheme,
            highlightCode = highlightCode
        )
    }

    private fun refreshState() {
        state = getCurrentViewState()
    }


    companion object {
        private const val TAG = "SettingsViewModel"
    }
}