package com.example.sample.base

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import com.example.sample.R

abstract class BaseActivity<out V : ViewDataBinding> : AppCompatActivity() {

    private lateinit var dataBinding: ViewDataBinding

    abstract fun getContentView(): Int

    fun getDataBinding(): ViewDataBinding = dataBinding

    fun setTitle(title: String) {
        actionBar?.title = title
        supportActionBar?.title = title
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        dataBinding = DataBindingUtil.setContentView<ViewDataBinding>(this, getContentView())
    }

    fun isNetworkConnected(): Boolean {
        val cm = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetwork: NetworkInfo? = cm.activeNetworkInfo
        var isConnected = false
        activeNetwork?.let {
            isConnected = it.isConnected
        }
        return isConnected
    }

    fun addFragment(fragment: BaseFragment<*, *>, tag: String) {
        supportFragmentManager?.beginTransaction()
            ?.add(R.id.container, fragment, tag)
            ?.addToBackStack(tag)?.commit()
    }
}