package com.example.sample.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.sample.pojo.Poster

@Dao
interface PosterDao {
    @get:Query("SELECT * FROM poster")
    val getAllPoster: List<Poster>

    @Insert
    fun insertPoster(product: Poster)

    @Query("SELECT * FROM poster WHERE  id = :id")
    fun findPosters(id: String): List<Poster>

    @Query("DELETE FROM poster WHERE id = :id")
    fun deletePoster(id: String)

    @Insert
    fun insertPosters(posters: List<Poster>)

    @Query("DELETE FROM poster")
    fun clearPosters()
}