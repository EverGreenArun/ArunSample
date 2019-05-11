package com.example.sample.pojo

import com.google.gson.annotations.SerializedName

data class Post(
    @SerializedName("id") var id: String?,
    @SerializedName("created_at") var createdAt: String?,
    @SerializedName("width") var width: Int?,
    @SerializedName("height") var height: Int?,
    @SerializedName("color") var color: String?,
    @SerializedName("likes") var likes: String?,
    @SerializedName("liked_by_user") var likedByUser: String?,
    @SerializedName("user") var user: User?,
    @SerializedName("current_user_collections") var currentUserCollections: ArrayList<String>?,
    @SerializedName("urls") var urls: Urls?,
    @SerializedName("categories") var categories: ArrayList<Category>?,
    @SerializedName("links") var links: Links?
)