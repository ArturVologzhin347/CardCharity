package com.example.cardcharity.presentation.component.dialog

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import com.example.cardcharity.R
import com.example.cardcharity.domen.auth.Authentication
import com.example.cardcharity.presentation.activities.splash.SplashActivity
import com.google.android.material.dialog.MaterialAlertDialogBuilder

class Dialog(private val context: Context) {
    lateinit var builder: MaterialAlertDialogBuilder

    fun create(title: String, message: String): Dialog {
        builder = MaterialAlertDialogBuilder(context).setTitle(title).setMessage(message)
        return this
    }

    fun create(title: String): Dialog {
        builder = MaterialAlertDialogBuilder(context).setTitle(title)
        return this
    }

    fun create(): Dialog {
        builder = MaterialAlertDialogBuilder(context)
        return this
    }

    fun setTitle(title: String?): Dialog {
        builder.setTitle(title)
        return this
    }

    fun enabledNegativeButton(title: String): Dialog {
        builder.setNegativeButton(title) { dialog: DialogInterface, _: Int -> dialog.dismiss() }
        return this
    }

    fun enabledPositiveButton(
        title: String,
        clickListener: DialogInterface.OnClickListener?
    ): Dialog {
        builder.setPositiveButton(title, clickListener)
        return this
    }

    fun enabledDismissPositiveButton(title: String): Dialog {
        builder.setPositiveButton(title) { dialog: DialogInterface, _: Int -> dialog.dismiss() }
        return this
    }


    fun show() {
        builder.create().show()
    }

    companion object {
        fun callSignOut(a: AppCompatActivity) = Dialog(a).create(
            a.getString(R.string.logout),
            a.getString(R.string.sure)
        ).enabledNegativeButton(a.getString(R.string.cancel))
            .enabledPositiveButton(a.getString(R.string.sign_out)) { _: DialogInterface?, _: Int ->
                Authentication.logout()

                val i = Intent(a, SplashActivity::class.java)
                i.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY)
                a.startActivity(i)
                a.finish()

            }.show()
    }
}