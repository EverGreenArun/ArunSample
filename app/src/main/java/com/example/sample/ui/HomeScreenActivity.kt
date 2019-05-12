package com.example.sample.ui

import android.os.Bundle
import androidx.databinding.ViewDataBinding
import com.example.sample.R
import com.example.sample.base.BaseActivity

class HomeScreenActivity : BaseActivity<ViewDataBinding>() {
    override fun getContentView(): Int = R.layout.activity_home_screen

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        addFragment(PostFragment.newInstance(), PostFragment::class.java.name)
    }
}