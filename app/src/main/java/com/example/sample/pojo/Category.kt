package com.example.sample.pojo

import androidx.room.ColumnInfo
import androidx.room.Embedded
import com.google.gson.annotations.SerializedName

data class Category(
    @ColumnInfo(name = "Category_id")
    @SerializedName("id") var id: Int?,

    @ColumnInfo(name = "title")
    @SerializedName("title") var title: String?,

    @ColumnInfo(name = "photo_count")
    @SerializedName("photo_count") var photoCount: Long?,

    @Embedded
    @SerializedName("links") var links: CategoryLinks?
)