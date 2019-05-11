package com.example.sample.pojo

import com.google.gson.annotations.SerializedName

data class ProfileImage(
    @SerializedName("small") var small: String?,
    @SerializedName("medium") var medium: String?,
    @SerializedName("large") var large: String?
)