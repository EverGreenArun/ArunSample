package com.example.sample.ui

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.sample.base.BaseViewModel
import com.example.sample.network.ApiFactory
import com.example.sample.pojo.Poster
import com.example.sample.repo.PostRepo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class PostersFragmentViewModel : BaseViewModel() {
    private val repository: PostRepo = PostRepo(ApiFactory.makeRetrofitService())

    val posters: ArrayList<Poster> = ArrayList()

    val liveData: MutableLiveData<Status> by lazy {
        MutableLiveData<Status>()
    }

    var isApiCallSuccess: Boolean = true

    var pageCount = 0

    fun fetchPosters() {
        if (pageCount < 10) {
            uiScope.launch {
                isApiCallSuccess = true
                fetchPostersInBackGround()
                liveData.value = if (isApiCallSuccess) {
                    pageCount += 1
                    Status.SUCCESS
                } else {
                    Status.FAILURE
                }
            }
        } else {
            liveData.value = Status.NO_MORE_DATA
        }
    }

    private suspend fun fetchPostersInBackGround() = withContext(Dispatchers.Default) {
        val posts = repository.getPosters()
        posts?.let {
            this@PostersFragmentViewModel.posters.addAll(it)
            return@withContext
        }
        if (posts.isNullOrEmpty()) {
            isApiCallSuccess = false
        }
    }

    class PostersFragmentViewModelFactory : ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return modelClass.getConstructor().newInstance()
        }
    }
}

enum class Status {
    SUCCESS, FAILURE, NO_MORE_DATA
}