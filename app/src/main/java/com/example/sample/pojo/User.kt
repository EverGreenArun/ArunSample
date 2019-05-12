package com.example.sample.pojo

import androidx.room.ColumnInfo
import androidx.room.Embedded
import com.google.gson.annotations.SerializedName

data class User(
    @ColumnInfo(name = "User_id")
    @SerializedName("id") var id: String?,

    @ColumnInfo(name = "username")
    @SerializedName("username") var username: String?,

    @ColumnInfo(name = "name")
    @SerializedName("name") var name: String?,

    @Embedded
    @SerializedName("profile_image") var profileImage: ProfileImage?,

    @Embedded
    @SerializedName("links") var links: UserLinks?
)