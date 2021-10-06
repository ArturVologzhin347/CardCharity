package com.example.cardcharity.presentation.activities.result

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.cardcharity.R
import com.example.cardcharity.databinding.ActivityResultBinding
import com.example.cardcharity.presentation.base.BaseActivity

/*
Progress bar or skeleton loading
Glide
transformation layout animation
 */
class ResultActivity : BaseActivity<ActivityResultBinding>(R.layout.activity_result) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val imageUrl = intent.getStringExtra(bundleImageUrl)
            ?: throw IllegalArgumentException("Image url cannot be null. Use show() function")

        Glide.with(this)
            .load(imageUrl)
            .centerCrop()
            .into(binding.testImageView)
    }



    companion object {
        private const val TAG = "ResultActivity"
        private const val bundleImageUrl = "${TAG}_imageUrl"

        fun show(activity: AppCompatActivity, imageUrl: String) {
            val i = Intent(activity, ResultActivity::class.java)
            i.putExtra(bundleImageUrl, imageUrl)
            activity.startActivity(i)
        }
    }


}