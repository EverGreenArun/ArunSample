package com.example.sample.pojo

import androidx.room.ColumnInfo
import com.google.gson.annotations.SerializedName

data class CategoryLinks(
    @ColumnInfo(name = "category_inks_self")
    @SerializedName("self") var self: String?,

    @ColumnInfo(name = "category_inks_html")
    @SerializedName("html") var html: String?,

    @ColumnInfo(name = "category_inks_photos")
    @SerializedName("photos") var photos: String?,

    @ColumnInfo(name = "category_inks_likes")
    @SerializedName("likes") var likes: String?,

    @ColumnInfo(name = "category_inks_download")
    @SerializedName("download") var download: String?
)