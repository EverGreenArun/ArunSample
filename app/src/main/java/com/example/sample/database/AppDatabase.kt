package com.example.sample.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.sample.pojo.*

@Database(entities = [Poster::class], version = 1)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun posterDao(): PosterDao

    companion object {
        private var INSTANCE: AppDatabase? = null
        private const val DB_NAME = "ifg_patient_db"
        private val lock = Any()

        fun getInstance(context: Context): AppDatabase {
            synchronized(lock) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.applicationContext, AppDatabase::class.java, DB_NAME).build()
                }
                return INSTANCE!!
            }
        }
    }
}