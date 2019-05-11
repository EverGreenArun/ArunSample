package com.example.downloaderlib.downloaderlib2.util

import com.example.downloaderlib.downloaderlib.FetchObserver

class ActiveDownloadInfo(val fetchObserver: FetchObserver<Boolean>,
                         val includeAddedDownloads: Boolean) {

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false
        other as ActiveDownloadInfo
        if (fetchObserver != other.fetchObserver) return false
        return true
    }

    override fun hashCode(): Int {
        return fetchObserver.hashCode()
    }

    override fun toString(): String {
        return "ActiveDownloadInfo(fetchObserver=$fetchObserver, includeAddedDownloads=$includeAddedDownloads)"
    }

}