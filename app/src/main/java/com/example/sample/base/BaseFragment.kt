package com.example.sample.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment

abstract class BaseFragment<out V : ViewDataBinding, out T : BaseViewModel> : Fragment() {

    private lateinit var mDataBinding: ViewDataBinding
    private lateinit var mViewModel: BaseViewModel

    abstract fun getViewModel(): BaseViewModel

    abstract fun getDataBinding(inflater: LayoutInflater, container: ViewGroup?): ViewDataBinding

    abstract fun setActionBarTitle()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        mDataBinding = getDataBinding(inflater, container)
        setActionBarTitle()
        return mDataBinding.root
    }

    fun isNetworkConnected() = (activity as BaseActivity<*>).isNetworkConnected()
}