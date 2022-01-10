package com.example.cardcharity.presentation.activities.password.forgot

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.compose.runtime.Composable
import com.example.cardcharity.presentation.base.BaseActivity

/*
- back button
- label (reset password)
- email field
- button (enabled only if email is valid), with loading and success states
- text
-


 */
class ForgotActivity : BaseActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val email = intent.getStringExtra(INTENT_EMAIL) ?: ""


    }

    @Composable
    override fun Screen() {
        ForgotScreen()
    }





    companion object {
        private const val TAG = "ForgotActivity"
        private const val INTENT_EMAIL = "${TAG}_email"

        fun start(context: Context, email: String) {
            val intent = Intent(context, ForgotActivity::class.java)
            intent.putExtra(INTENT_EMAIL, email)
            context.startActivity(intent)
        }
    }
}