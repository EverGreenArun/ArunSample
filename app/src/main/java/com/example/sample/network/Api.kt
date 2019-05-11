package com.example.sample.network

import com.example.sample.pojo.Post
import kotlinx.coroutines.Deferred
import retrofit2.Response
import retrofit2.http.GET

interface Api {
    @GET("/raw/wgkJgazE")
    fun getPostsAsync(): Deferred<Response<ArrayList<Post>>>
}