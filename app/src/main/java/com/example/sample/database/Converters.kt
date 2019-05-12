package com.example.sample.database

import androidx.room.TypeConverter
import com.example.sample.pojo.Category
import com.google.gson.Gson

class Converters {
    companion object {
        @TypeConverter
        @JvmStatic
        fun categoryListToJson(value: ArrayList<Category>?): String {
            return Gson().toJson(value)
        }

        @TypeConverter
        @JvmStatic
        fun jsonToCategoryList(value: String): ArrayList<Category>? {
            val objects = Gson().fromJson(value, Array<Category>::class.java) as Array<Category>
            return ArrayList(objects.toList())
        }

        @TypeConverter
        @JvmStatic
        fun stringListToJson(value: ArrayList<String>?): String {
            return Gson().toJson(value)
        }

        @TypeConverter
        @JvmStatic
        fun jsonToStringList(value: String): ArrayList<String>? {
            val objects = Gson().fromJson(value, Array<String>::class.java) as Array<String>
            return ArrayList(objects.toList())
        }
    }
}