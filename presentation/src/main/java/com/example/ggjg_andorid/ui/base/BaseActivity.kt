package com.example.ggjg_andorid.ui.base

import android.os.Bundle
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding

abstract class BaseActivity<B: ViewDataBinding>(
    @LayoutRes private val layoutId: Int
): AppCompatActivity() {

    protected lateinit var binding: B
    private var onKeyBackPressedListener: OnKeyBackPressedListener? = null

    interface OnKeyBackPressedListener {
        fun onBack()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this,layoutId)
        binding.lifecycleOwner = this

        createView()
    }

    override fun onBackPressed() {
        if (onKeyBackPressedListener != null) {
            (onKeyBackPressedListener as OnKeyBackPressedListener).onBack()
        } else {
            super.onBackPressed()
        }
    }

    abstract fun createView()

    fun setOnKeyBackPressedListener(listener: OnKeyBackPressedListener) {
        onKeyBackPressedListener = listener
    }

    fun deleteOnKeyBackPressedListener() {
        onKeyBackPressedListener = null
    }
}