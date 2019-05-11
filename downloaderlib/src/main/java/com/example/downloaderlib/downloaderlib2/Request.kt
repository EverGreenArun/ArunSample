package com.example.downloaderlib.downloaderlib2

import android.net.Uri
import android.os.Parcel
import android.os.Parcelable
import com.example.downloaderlib.downloaderlib.Extras
import com.example.downloaderlib.downloaderlib.getFileUri
import com.example.downloaderlib.downloaderlib.getUniqueId
import java.io.Serializable

/**
 * Use this class to create a request that is used by Fetch to enqueue a download and
 * begin the download process.
 * */
open class Request constructor(
        /** The url where the file will be downloaded from.*/
        val url: String,

        /** The file eg(/files/download.txt) where the file will be
         * downloaded to and saved on disk.*/
        val file: String) : RequestInfo(), Parcelable, Serializable {

    constructor(
            /** The url where the file will be downloaded from.*/
            url: String,
            /** The file uri eg(file:///files/download.txt or
             * content://com.contentprovider.provider/data/download.txt) where the file will be
             * downloaded to and saved on disk.*/
            fileUri: Uri) : this(url, fileUri.toString())

    /** Unique Identifier. Used to identify a download.*/
    val id: Int = getUniqueId(url, file)

    /** Returns the FileUri.*/
    val fileUri: Uri
        get() {
            return getFileUri(file)
        }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false
        if (!super.equals(other)) return false
        other as Request
        if (id != other.id) return false
        if (url != other.url) return false
        if (file != other.file) return false
        return true
    }

    override fun hashCode(): Int {
        var result = super.hashCode()
        result = 31 * result + id
        result = 31 * result + url.hashCode()
        result = 31 * result + file.hashCode()
        return result
    }

    override fun toString(): String {
        return "Request(url='$url', file='$file', id=$id, groupId=$groupId, " +
                "headers=$headers, priority=$priority, networkType=$networkType, tag=$tag)"
    }

    override fun writeToParcel(dest: Parcel?, flags: Int) {
        dest?.writeString(url)
        dest?.writeString(file)
        dest?.writeLong(identifier)
        dest?.writeInt(groupId)
        dest?.writeSerializable(HashMap(headers))
        dest?.writeInt(priority.value)
        dest?.writeInt(networkType.value)
        dest?.writeString(tag)
        dest?.writeInt(enqueueAction.value)
        dest?.writeInt(if (downloadOnEnqueue) 1 else 0)
        dest?.writeSerializable(HashMap(extras.map))
        dest?.writeInt(autoRetryMaxAttempts)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Request> {

        @Suppress("UNCHECKED_CAST")
        override fun createFromParcel(input: Parcel): Request {
            val url = input.readString() ?: ""
            val file = input.readString() ?: ""
            val identifier = input.readLong()
            val groupId = input.readInt()
            val headers = input.readSerializable() as Map<String, String>
            val priority = Priority.valueOf(input.readInt())
            val networkType = NetworkType.valueOf(input.readInt())
            val tag = input.readString()
            val enqueueAction = EnqueueAction.valueOf(input.readInt())
            val downloadOnEnqueue = input.readInt() == 1
            val extras = input.readSerializable() as Map<String, String>
            val autoRetryMaxAttempts = input.readInt()
            val autoRetryAttempts = input.readInt()
            val request = Request(url, file)
            request.identifier = identifier
            request.groupId = groupId
            headers.forEach {
                request.addHeader(it.key, it.value)
            }
            request.priority = priority
            request.networkType = networkType
            request.tag = tag
            request.enqueueAction = enqueueAction
            request.downloadOnEnqueue = downloadOnEnqueue
            request.extras = Extras(extras)
            request.autoRetryMaxAttempts = autoRetryMaxAttempts
            return request
        }

        override fun newArray(size: Int): Array<Request?> {
            return arrayOfNulls(size)
        }
    }

}