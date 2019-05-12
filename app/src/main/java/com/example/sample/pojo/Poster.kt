package com.example.sample.pojo

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "poster")
data class Poster(
    @PrimaryKey(autoGenerate = true) val poster_db_id: Int,

    @ColumnInfo(name = "id")
    @SerializedName("id") var id: String?,

    @ColumnInfo(name = "created_at")
    @SerializedName("created_at") var createdAt: String?,

    @ColumnInfo(name = "width")
    @SerializedName("width") var width: Int?,

    @ColumnInfo(name = "height")
    @SerializedName("height") var height: Int?,

    @ColumnInfo(name = "color")
    @SerializedName("color") var color: String?,

    @ColumnInfo(name = "likes")
    @SerializedName("likes") var likes: String?,

    @ColumnInfo(name = "liked_by_user")
    @SerializedName("liked_by_user") var likedByUser: String?,

    @Embedded
    @SerializedName("user") var user: User?,

    @ColumnInfo(name = "current_user_collections")
    @SerializedName("current_user_collections") var currentUserCollections: ArrayList<String>?,

    @Embedded
    @SerializedName("urls") var urls: Urls?,

    @ColumnInfo(name = "categories")
    @SerializedName("categories") var categories: ArrayList<Category>?,

    @Embedded
    @SerializedName("links") var posterLinks: PosterLinks?
)