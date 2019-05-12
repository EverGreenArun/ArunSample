package com.example.sample.network

import com.example.sample.pojo.Poster
import kotlinx.coroutines.Deferred
import retrofit2.Response
import retrofit2.http.GET

interface Api {
    @GET("/raw/wgkJgazE")
    fun getPostersAsync(): Deferred<Response<ArrayList<Poster>>>
}