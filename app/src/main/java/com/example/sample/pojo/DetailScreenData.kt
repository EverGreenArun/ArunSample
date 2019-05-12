package com.example.sample.pojo

import android.os.Parcel
import android.os.Parcelable

data class DetailScreenData(var fileType: String,var fileUri: String,var title: String):Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString(),
        parcel.readString()
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(fileType)
        parcel.writeString(fileUri)
        parcel.writeString(title)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<DetailScreenData> {
        override fun createFromParcel(parcel: Parcel): DetailScreenData {
            return DetailScreenData(parcel)
        }

        override fun newArray(size: Int): Array<DetailScreenData?> {
            return arrayOfNulls(size)
        }
    }
}