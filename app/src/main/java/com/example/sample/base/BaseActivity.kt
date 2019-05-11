package com.example.sample.base

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding

abstract class BaseActivity<out V : ViewDataBinding>: AppCompatActivity() {

    private lateinit var dataBinding: ViewDataBinding

    abstract fun getContentView(): Int

    fun getDataBinding(): ViewDataBinding = dataBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        dataBinding = DataBindingUtil.setContentView<ViewDataBinding>(this, getContentView())
    }
}