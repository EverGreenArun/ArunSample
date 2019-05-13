package com.example.sample.repo

import android.content.Context
import androidx.annotation.WorkerThread
import com.example.sample.database.AppDatabase
import com.example.sample.pojo.Poster

object PosterLocalRepo {
    @WorkerThread
    fun initDB(context: Context) {
        AppDatabase.getInstance(context).posterDao().getAllPoster
    }

    @WorkerThread
    fun getAllPoster(context: Context) : List<Poster> = AppDatabase.getInstance(context).posterDao().getAllPoster

    @WorkerThread
    fun insertAllPoster(context: Context, posters:List<Poster>) = AppDatabase.getInstance(context).posterDao().insertPosters(posters)

    @WorkerThread
    fun clearAllPosters(context: Context) = AppDatabase.getInstance(context).posterDao().clearPosters()
}
