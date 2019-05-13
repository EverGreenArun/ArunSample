package com.example.sample.ui

import android.app.Application
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.sample.base.BaseViewModel
import com.example.sample.network.ApiFactory
import com.example.sample.pojo.Poster
import com.example.sample.repo.PosterLocalRepo
import com.example.sample.repo.PosterRemoteRepo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class PostersFragmentViewModel(application: Application) : BaseViewModel(application) {
    private val repository: PosterRemoteRepo = PosterRemoteRepo(ApiFactory.makeRetrofitService())

    val posters: ArrayList<Poster> = ArrayList()

    val liveData: MutableLiveData<Status> by lazy {
        MutableLiveData<Status>()
    }

    private var isApiCallSuccess: Boolean = true

    private var pageCount = 0

    var offlineDataLoaded: Boolean = false

    fun fetchPosters() {
        if (pageCount < 10) {
            if (pageCount == 0) {
                getAllPosters()
            }
            uiScope.launch {
                isApiCallSuccess = true
                fetchPostersInBackGround()
                liveData.value = if (isApiCallSuccess) {
                    pageCount += 1
                    if (pageCount == 1) {
                        clearAllPosters()
                        insertAllPoster(posters)
                    }
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


    fun getAllPosters() {
        uiScope.launch {
            var list: List<Poster>? = null
            val query = async(Dispatchers.IO) {
                // Async stuff
                list = PosterLocalRepo.getAllPoster(getApplication())
            }
            query.await()
            list?.let {
                posters.clear()
                posters.addAll(it)
                liveData.value = Status.OLD_DATA
            }
        }
    }

    private fun insertAllPoster(posters: List<Poster>) {
        uiScope.launch {
            val query = async(Dispatchers.IO) {
                // Async stuff
                PosterLocalRepo.insertAllPoster(getApplication(), posters)
            }
            query.await()
        }
    }

    private fun clearAllPosters() {
        uiScope.launch {
            val query = async(Dispatchers.IO) {
                // Async stuff
                PosterLocalRepo.clearAllPosters(getApplication())
            }
            query.await()
        }
    }

    class PostersFragmentViewModelFactory(private val application: Application) : ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return modelClass.getConstructor(Application::class.java).newInstance(application)
        }
    }
}

enum class Status {
    SUCCESS, FAILURE, NO_MORE_DATA, OLD_DATA
}