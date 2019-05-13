package com.example.sample.repo

import android.content.Context
import com.example.sample.database.AppDatabase
import com.example.sample.pojo.Poster

object PosterLocalRepo {
    fun getAllPoster(context: Context) : List<Poster> = AppDatabase.getInstance(context).posterDao().getGetAllPoster()

    fun insertAllPoster(context: Context, posters:List<Poster>) = AppDatabase.getInstance(context).posterDao().insertPosters(posters)

    fun clearAllPosters(context: Context) = AppDatabase.getInstance(context).posterDao().clearPosters()
}
