package com.example.cardcharity.presentation.activities.main.qr

import android.graphics.Bitmap
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.graphics.drawable.GradientDrawable
import android.os.Bundle
import android.view.View
import android.widget.FrameLayout
import androidx.fragment.app.FragmentManager
import androidx.palette.graphics.Palette
import com.bumptech.glide.Priority
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import com.example.cardcharity.R
import com.example.cardcharity.databinding.ActivityMainQrSheetBinding
import com.example.cardcharity.domen.auth.Authentication
import com.example.cardcharity.domen.glide.GlideApp
import com.example.cardcharity.presentation.base.BaseBottomSheet
import com.example.cardcharity.repository.model.Shop
import com.example.cardcharity.repository.preferences.Preferences
import com.example.cardcharity.utils.getBrightness
import com.example.cardcharity.utils.setScreenBrightness
import com.google.android.material.bottomsheet.BottomSheetBehavior

class QrBottomSheet : BaseBottomSheet<ActivityMainQrSheetBinding>(R.layout.activity_main_qr_sheet) {
    private var _shop: Shop? = null
    private var previousBrightness = 0F

    private val shop: Shop
        get() = _shop!!

    fun display(fm: FragmentManager, shop: Shop) {
        super.display(fm, TAG)
        this._shop = shop
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.apply {
            if (_shop == null) {
                dismiss()
            }

            loadLogo()
            loadQr(shop.getCodeUrl(Authentication.uid))
        }
    }

    private fun loadLogo() {
        GlideApp.with(this@QrBottomSheet)
            .asBitmap()
            .load(shop.getLogoUrl())
            .circleCrop()
            .into(object : CustomTarget<Bitmap>() {
                override fun onResourceReady(resource: Bitmap, transition: Transition<in Bitmap>?) {
                    binding.imageViewLogo.setImageBitmap(resource)
                    Palette.from(resource).generate { palette ->
                        palette?.apply {
                            val c1 = dominantSwatch?.rgb


                            if (c1 != null) {
                                val background = GradientDrawable(
                                    GradientDrawable.Orientation.BOTTOM_TOP,
                                    intArrayOf(c1, Color.TRANSPARENT)
                                )

                                //TODO
                                //binding.constraintLayout.background = background
                            }
                        }
                    }
                }

                override fun onLoadCleared(placeholder: Drawable?) {

                }

            })
    }


    private fun loadQr(url: String) {
        GlideApp.with(this@QrBottomSheet)
            .load(url)
            .transition(DrawableTransitionOptions.withCrossFade())
            .centerCrop()
            .diskCacheStrategy(DiskCacheStrategy.NONE)
            .skipMemoryCache(true)
            .priority(Priority.HIGH)
            .into(binding.imageViewQr)
    }


    override fun getTheme(): Int {
        return R.style.CustomBottomSheetDialog
    }

    override fun onStart() {
        super.onStart()
        setMaxBrightness()


        val density = requireContext().resources.displayMetrics.density

        //TODO
        dialog?.let {
            val bottomSheet =
                it.findViewById<View>(com.google.android.material.R.id.design_bottom_sheet) as FrameLayout
            val behavior = BottomSheetBehavior.from(bottomSheet)

            behavior.peekHeight = (COLLAPSED_HEIGHT * density).toInt()
            behavior.state = BottomSheetBehavior.STATE_COLLAPSED

            behavior.addBottomSheetCallback(object : BottomSheetBehavior.BottomSheetCallback() {
                override fun onStateChanged(bottomSheet: View, newState: Int) {

                }

                override fun onSlide(bottomSheet: View, slideOffset: Float) {

                }

            })
        }
    }

    override fun onStop() {
        super.onStop()
        setPreviousBrightness()
    }

    private fun setMaxBrightness() {
        if (Preferences.barcodeIllumination) {
            window.apply {
                previousBrightness = getBrightness()
                setScreenBrightness(MAX_BRIGHTNESS)
            }
        }
    }

    private fun setPreviousBrightness() {
        if (Preferences.barcodeIllumination) {
            window.apply {
                setScreenBrightness(previousBrightness)
            }
        }
    }

    companion object {
        private const val TAG = "QrBottomSheet"
        private const val MAX_BRIGHTNESS = 1f
        private const val COLLAPSED_HEIGHT = 500

        fun display(fm: FragmentManager, shop: Shop) {
            QrBottomSheet().display(fm, shop)
        }
    }
}