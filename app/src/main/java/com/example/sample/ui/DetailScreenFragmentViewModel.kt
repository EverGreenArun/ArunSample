package com.example.sample.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.sample.base.BaseViewModel
import com.example.sample.pojo.DetailScreenData

class DetailScreenFragmentViewModel : BaseViewModel() {
    var detailScreenData: DetailScreenData? = null

    class DetailScreenFragmentViewModelFactory : ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return modelClass.getConstructor().newInstance()
        }
    }
}