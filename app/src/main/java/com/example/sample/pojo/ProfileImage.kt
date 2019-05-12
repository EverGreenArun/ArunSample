package com.example.sample.pojo

import androidx.room.ColumnInfo
import com.google.gson.annotations.SerializedName

data class ProfileImage(
    @ColumnInfo(name = "profile_image_small")
    @SerializedName("small") var small: String?,

    @ColumnInfo(name = "profile_image_medium")
    @SerializedName("medium") var medium: String?,

    @ColumnInfo(name = "profile_image_large")
    @SerializedName("large") var large: String?
)