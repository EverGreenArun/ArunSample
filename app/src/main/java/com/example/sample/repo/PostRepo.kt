package com.example.sample.repo

import com.example.sample.base.BaseRepository
import com.example.sample.network.Api
import com.example.sample.pojo.Poster

class PostRepo(private val api: Api):BaseRepository() {
    suspend fun getPosters() : ArrayList<Poster>?{
        return safeApiCall(
        call = { api.getPostersAsync().await()},
        errorMessage = "Error Fetching Popular Movies")
    }
}