package com.example.sample.pojo

import com.google.gson.annotations.SerializedName

data class Links(@SerializedName("self") var self: String?,
                 @SerializedName("html") var html:String?,
                 @SerializedName("photos") var photos:String?,
                 @SerializedName("likes") var likes:String?)