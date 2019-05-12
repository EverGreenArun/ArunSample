package com.example.sample.ui

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.sample.base.BaseViewModel
import com.example.sample.pojo.DetailScreenData

class DetailScreenFragmentViewModel(application: Application) : BaseViewModel(application) {
    var detailScreenData: DetailScreenData? = null

    class DetailScreenFragmentViewModelFactory(private val application: Application) : ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return modelClass.getConstructor(Application::class.java).newInstance(application)
        }
    }
}