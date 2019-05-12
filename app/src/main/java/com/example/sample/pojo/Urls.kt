package com.example.sample.pojo

import androidx.room.ColumnInfo
import com.google.gson.annotations.SerializedName

data class Urls(
    @ColumnInfo(name = "urls_raw")
    @SerializedName("raw") var raw: String?,

    @ColumnInfo(name = "urls_full")
    @SerializedName("full") var full: String?,

    @ColumnInfo(name = "urls_regular")
    @SerializedName("regular") var regular: String?,

    @ColumnInfo(name = "urls_small")
    @SerializedName("small") var small: String?,

    @ColumnInfo(name = "urls_thumb")
    @SerializedName("thumb") var thumb: String?
)