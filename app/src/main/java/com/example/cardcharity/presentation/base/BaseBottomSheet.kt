package com.example.cardcharity.presentation.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.FragmentManager
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

abstract class BaseBottomSheet<VDB : ViewDataBinding>(
    @LayoutRes private val layoutResId: Int
) : BottomSheetDialogFragment() {

    private var _binding: VDB? = null
    protected val binding: VDB get() = checkNotNull(_binding)

    protected val window: Window
        get() = requireActivity().window

    protected fun display(fm: FragmentManager, tag: String) {
        show(fm, tag)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = DataBindingUtil.inflate(
            inflater,
            layoutResId,
            container,
            false
        )
        return binding.root
    }


    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}