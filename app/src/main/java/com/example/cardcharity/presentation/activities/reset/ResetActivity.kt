package com.example.cardcharity.presentation.activities.reset

import android.content.Context
import android.content.Intent
import androidx.activity.viewModels
import androidx.compose.runtime.Composable
import com.example.cardcharity.presentation.base.mvi.MviActivity

class ResetActivity : MviActivity<ResetViewState, ResetEvent, ResetViewModel>() {
    override val viewModel: ResetViewModel by viewModels()

    @Composable
    override fun Screen(reduce: (event: ResetEvent) -> Unit, viewState: ResetViewState) {
        val email = intent.getStringExtra(BUNDLE_EMAIL) ?: ""

        ResetPasswordScreen(
            reduce = reduce,
            viewState = viewState,
            initialEmail = email
        )
    }

    override fun reduceEvent(event: ResetEvent) {
        when (event) {
            Finish -> this.finish()
            else -> viewModel.reduceEvent(event)
        }

    }

    companion object {
        private const val TAG = "ResetPasswordActivity"
        private const val BUNDLE_EMAIL = "${TAG}.email"


        fun start(context: Context, email: String) {
            Intent(context, ResetActivity::class.java).apply {
                putExtra(BUNDLE_EMAIL, email)
                context.startActivity(this)
            }
        }
    }
}
