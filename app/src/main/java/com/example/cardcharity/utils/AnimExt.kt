package com.example.cardcharity.utils

import android.animation.AnimatorInflater
import android.animation.AnimatorSet
import android.content.Context
import androidx.annotation.AnimatorRes

fun Context.loadAnimator(@AnimatorRes animatorResId: Int): AnimatorSet {
    return AnimatorInflater.loadAnimator(this, animatorResId) as AnimatorSet
}