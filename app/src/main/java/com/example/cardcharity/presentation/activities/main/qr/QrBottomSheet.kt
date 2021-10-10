package com.example.cardcharity.presentation.activities.main.qr

import android.os.Bundle
import android.view.View
import androidx.fragment.app.FragmentManager
import com.bumptech.glide.Glide
import com.example.cardcharity.R
import com.example.cardcharity.databinding.ActivityMainQrSheetBinding
import com.example.cardcharity.presentation.base.BaseBottomSheet
import com.example.cardcharity.repository.model.Shop
import com.example.cardcharity.repository.preferences.Preferences
import com.example.cardcharity.utils.getBrightness
import com.example.cardcharity.utils.setScreenBrightness

class QrBottomSheet : BaseBottomSheet<ActivityMainQrSheetBinding>(R.layout.activity_main_qr_sheet) {
    private var shop: Shop? = null
    private var previousBrightness = 0F


    fun display(fm: FragmentManager, shop: Shop) {
        super.display(fm, TAG)
        this.shop = shop
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (shop != null) {
            //Shop name
            //binding.textViewShopName.text = shop!!.name

            //Shop logo
            // Glide.with(this).load(shop!!.imageUrl).circleCrop().into(binding.imageViewShopLogo)

            //QR
            Glide.with(this)
                .load(shop!!.qrUrl)
                .centerCrop()
                .into(binding.imageViewQr)

        } else {
            dismiss()
        }
    }

    override fun getTheme(): Int {
        return R.style.CustomBottomSheetDialog
    }

    override fun onStart() {
        super.onStart()
        setMaxBrightness()
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

        fun display(fm: FragmentManager, shop: Shop) {
            QrBottomSheet().display(fm, shop)
        }
    }
}