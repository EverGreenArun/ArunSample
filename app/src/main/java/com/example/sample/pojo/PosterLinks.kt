package com.example.sample.pojo

import androidx.room.ColumnInfo
import com.google.gson.annotations.SerializedName

data class PosterLinks(
    @ColumnInfo(name = "poster_links_self")
    @SerializedName("poster_links_self") var self: String?,

    @ColumnInfo(name = "poster_links_html")
    @SerializedName("html") var html: String?,

    @ColumnInfo(name = "poster_links_photos")
    @SerializedName("photos") var photos: String?,

    @ColumnInfo(name = "poster_links_likes")
    @SerializedName("likes") var likes: String?,

    @ColumnInfo(name = "poster_links_download")
    @SerializedName("download") var download: String?
)