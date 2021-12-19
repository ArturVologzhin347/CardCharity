package com.example.cardcharity.presentation.anim

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.AnimatorSet
import android.content.Context
import android.view.View
import androidx.annotation.AnimatorRes
import androidx.core.view.isVisible
import com.example.cardcharity.R
import com.example.cardcharity.utils.extensions.loadAnimator

class HideableManager(private val context: Context) {
    private var animatorIn = context.loadAnimator(R.animator.translation_y_alpha_in)
    private var animatorOut = context.loadAnimator(R.animator.alpha_out)
    private lateinit var anchor: View

    constructor(context: Context, anchor: View): this(context) {
        attach(anchor)
    }

    private var isVisible: Boolean = false

    var hide: Boolean
        get() = !isVisible
        set(value) = set(value)

    var show: Boolean
        get() = isVisible
        set(value) = set(!value)

    init {
        animatorOut.addListener(object : AnimatorListenerAdapter() {
            override fun onAnimationCancel(animation: Animator?) {
                super.onAnimationCancel(animation)
            }

            override fun onAnimationEnd(animation: Animator?) {
                super.onAnimationEnd(animation)
                assertAnchor()
                anchor.isVisible = false
                isVisible = false
            }
        })
    }

    fun attach(anchor: View) {
        this.anchor = anchor
    }

    fun setAnimators(animatorIn: AnimatorSet, animatorOut: AnimatorSet) {
        this.animatorIn = animatorIn
        this.animatorOut = animatorOut
    }

    fun setAnimators(@AnimatorRes animatorInResId: Int, @AnimatorRes animatorOutResId: Int) {
        this.animatorIn = context.loadAnimator(animatorInResId)
        this.animatorOut = context.loadAnimator(animatorOutResId)
    }

    fun show() {
        assertAnchor()
        if (isVisible) return
        anchor.isVisible = true
        animatorIn.setTarget(anchor)
        animatorIn.start()
        isVisible = true
    }

    fun hide() {
        assertAnchor()
        if (!isVisible) return
        animatorOut.setTarget(anchor)
        animatorOut.start()
    }

    fun set(hide: Boolean) {
        if (hide) hide() else show()
    }

    private fun assertAnchor() {
        if (!::anchor.isInitialized) {
            throw RuntimeException("Anchor must be attached. Use the method attach()")
        }
    }
}