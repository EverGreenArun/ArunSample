package com.example.sample.ui

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.databinding.ViewDataBinding
import com.example.sample.R
import com.example.sample.base.BaseActivity
import com.example.sample.databinding.ActivityHomeScreenBinding
import com.example.sample.network.ApiFactory
import com.example.sample.pojo.Post
import com.example.sample.repo.PostRepo
import com.example.sample.ui.adapter.PostAdapter
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

class HomeScreenActivity : BaseActivity<ViewDataBinding>() {
    private val dataBinding: ActivityHomeScreenBinding by lazy {
        getDataBinding() as ActivityHomeScreenBinding
    }

    private val posts :ArrayList<Post> = ArrayList()
    private lateinit var postAdapter: PostAdapter

    override fun getContentView(): Int = R.layout.activity_home_screen

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        dataBinding.emptyMsg = "network error"
        dataBinding.executePendingBindings()
        postAdapter = PostAdapter(posts)
        dataBinding.recyclerPost.adapter = postAdapter
        fetchPosts()
    }

    private val parentJob = Job()

    private val coroutineContext: CoroutineContext
        get() = parentJob + Dispatchers.Default

    private val scope = CoroutineScope(coroutineContext)

    private val repository : PostRepo = PostRepo(ApiFactory.makeRetrofitService())


    private fun fetchPosts(){
        scope.launch {
            val posts = repository.getPosts()
            posts?.let { this@HomeScreenActivity.posts.addAll(it) }
            Handler(Looper.getMainLooper()).post {postAdapter.notifyDataSetChanged()}
        }
    }


}