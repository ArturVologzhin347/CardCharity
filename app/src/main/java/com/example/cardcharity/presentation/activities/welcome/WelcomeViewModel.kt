package com.example.cardcharity.presentation.activities.welcome

import android.app.Application
import com.example.cardcharity.di.Dagger2
import com.example.cardcharity.presentation.base.mvi.MviViewModel
import com.example.cardcharity.repository.preferences.Preferences
import javax.inject.Inject

class WelcomeViewModel(application: Application) :
    MviViewModel<WelcomeViewState, WelcomeEvent>(default(), application) {

    @Inject
    lateinit var preferences: Preferences


    override fun inject(dagger: Dagger2) {
        super.inject(dagger)
        dagger.repositoryComponent.inject(this)
    }

    fun visit() {
        preferences.hasVisited = true
    }
}
