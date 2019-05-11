package com.example.sample.pojo

import com.google.gson.annotations.SerializedName

data class Links(@SerializedName("self") var small: String?,
                 @SerializedName("html") var medium:String?,
                 @SerializedName("photos") var large:String?,
                 @SerializedName("likes") var likes:String?)