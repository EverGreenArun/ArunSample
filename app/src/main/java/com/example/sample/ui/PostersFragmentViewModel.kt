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
    val liveData: MutableLiveData<Status> by lazy {
        MutableLiveData<Status>()
    }
    val posters: ArrayList<Poster> = ArrayList()

    private val maxPageCount = 10
    private var numberOfPagesLoaded = 0

    var offlineDataLoaded: Boolean = false


    /**
     *Loads data from remote or local storage
     * */
    fun getPosters() {
        //Max page
        if (numberOfPagesLoaded < maxPageCount) {
            if (numberOfPagesLoaded == 0) {
                //load older data from storage, till completing first remote call
                getAllPostersFromLocalStorage()
            }
            uiScope.launch {
                val list = getPostersFromRemoteStorage()
                list?.let {
                    //for first remote call success have to clear old data
                    if (numberOfPagesLoaded == 0) {
                        posters.clear()
                        clearAllPostersInLocalStorage()
                    }
                    posters.addAll(it)
                    insertAllPosterToLocalStorage(it)
                    numberOfPagesLoaded += 1
                    liveData.value = Status.SUCCESS
                }
                if (list == null) {
                    liveData.value = Status.FAILURE
                }
            }
        } else {
            liveData.value = Status.NO_MORE_DATA
        }
    }

    private suspend fun getPostersFromRemoteStorage(): ArrayList<Poster>? = withContext(Dispatchers.IO) {
        return@withContext repository.getPosters()
    }


    fun getAllPostersFromLocalStorage() {
        uiScope.launch {
            var list: List<Poster>? = null
            val query = async(Dispatchers.IO) {
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

    private fun insertAllPosterToLocalStorage(posters: List<Poster>) {
        uiScope.launch {
            val query = async(Dispatchers.IO) {
                PosterLocalRepo.insertAllPoster(getApplication(), posters)
            }
            query.await()
        }
    }

    private fun clearAllPostersInLocalStorage() {
        uiScope.launch {
            val query = async(Dispatchers.IO) {
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