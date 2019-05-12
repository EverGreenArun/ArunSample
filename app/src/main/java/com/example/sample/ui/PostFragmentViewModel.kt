package com.example.sample.ui

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.sample.base.BaseViewModel
import com.example.sample.network.ApiFactory
import com.example.sample.pojo.Post
import com.example.sample.repo.PostRepo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class PostFragmentViewModel : BaseViewModel() {
    private val repository: PostRepo = PostRepo(ApiFactory.makeRetrofitService())

    val posts: ArrayList<Post> = ArrayList()

    val liveData: MutableLiveData<Status> by lazy {
        MutableLiveData<Status>()
    }

    var isApiCallSuccess: Boolean = true

    fun fetchPosts() {
        uiScope.launch {
            isApiCallSuccess = true
            fetchPostsBackGround()
            liveData.value = if (isApiCallSuccess) {
                Status.SUCCESS
            } else {
                Status.FAILURE
            }
        }
    }

    private suspend fun fetchPostsBackGround() = withContext(Dispatchers.Default) {
        val posts = repository.getPosts()
        posts?.let {
            this@PostFragmentViewModel.posts.addAll(it)
            return@withContext
        }
        if (posts.isNullOrEmpty()) {
            isApiCallSuccess = false
        }
    }

    class PostFragmentViewModelFactory : ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return modelClass.getConstructor().newInstance()
        }
    }
}

enum class Status {
    SUCCESS, FAILURE
}