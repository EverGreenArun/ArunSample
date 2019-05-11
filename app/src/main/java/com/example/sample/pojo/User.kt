package com.example.sample.pojo

import com.google.gson.annotations.SerializedName

data class User(
    @SerializedName("id") var id: String?,
    @SerializedName("username") var username: String?,
    @SerializedName("name") var name: String?,
    @SerializedName("profile_image") var profileImage: ProfileImage?,
    @SerializedName("links") var links: Links?
)