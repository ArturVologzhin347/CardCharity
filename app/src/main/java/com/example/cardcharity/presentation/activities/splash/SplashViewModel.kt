package com.example.cardcharity.presentation.activities.splash

import android.app.Application
import android.content.Intent
import com.example.cardcharity.di.Dagger2
import com.example.cardcharity.domain.auth.Authorization
import com.example.cardcharity.presentation.activities.auth.login.LoginActivity
import com.example.cardcharity.presentation.activities.main.MainActivity
import com.example.cardcharity.presentation.activities.welcome.WelcomeActivity
import com.example.cardcharity.presentation.base.BaseViewModel
import com.example.cardcharity.repository.model.User
import com.example.cardcharity.repository.preferences.Preferences
import com.example.cardcharity.utils.extensions.isAuthorized
import javax.inject.Inject
import kotlin.reflect.KClass

class SplashViewModel(application: Application) : BaseViewModel(application) {

    @Inject
    lateinit var preferences: Preferences

    override fun inject(dagger: Dagger2) {
        super.inject(dagger)
        dagger.repositoryComponent.inject(this)
    }

    fun next(user: User?) {
        val destination = getDestination(user)
        val intent = Intent(context, destination.java).apply {
            flags = flags or Intent.FLAG_ACTIVITY_NEW_TASK
        }

        context.startActivity(intent)
    }


    private fun getDestination(user: User?): KClass<*> {
        if(!preferences.hasVisited) {
            return WelcomeActivity::class
        }

        if(user.isAuthorized) {
            return MainActivity::class
        }

        return LoginActivity::class
    }
}