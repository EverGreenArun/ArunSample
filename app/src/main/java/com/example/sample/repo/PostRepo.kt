package com.example.sample.repo

import com.example.sample.base.BaseRepository
import com.example.sample.network.Api
import com.example.sample.pojo.Post

class PostRepo(private val api: Api):BaseRepository() {
    suspend fun getPosts() : ArrayList<Post>?{
        return safeApiCall(
        call = { api.getPostsAsync().await()},
        errorMessage = "Error Fetching Popular Movies")
    }
}