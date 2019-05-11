package com.example.sample.pojo

import com.google.gson.annotations.SerializedName

data class Category(
    @SerializedName("id") var id: Int?,
    @SerializedName("title") var title: String?,
    @SerializedName("photo_count") var photoCount: Long?,
    @SerializedName("links") var links: Links
)