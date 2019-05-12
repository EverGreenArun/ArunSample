package com.example.sample.pojo

import androidx.room.ColumnInfo
import com.google.gson.annotations.SerializedName

data class UserLinks(
    @ColumnInfo(name = "user_links_self")
    @SerializedName("self") var self: String?,

    @ColumnInfo(name = "user_links_html")
    @SerializedName("html") var html: String?,

    @ColumnInfo(name = "user_links_photos")
    @SerializedName("photos") var photos: String?,

    @ColumnInfo(name = "user_links_likes")
    @SerializedName("likes") var likes: String?,

    @ColumnInfo(name = "user_links_download")
    @SerializedName("download") var download: String?
)