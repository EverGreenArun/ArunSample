package com.example.downloaderlib.downloaderlib

/**
 * Fetch observers listen for data changes on Objects that are produced by Fetch.
 * eg: FetchGroup, etc.
 * */
@FunctionalInterface
interface FetchObserver<T> {

    /**
     * Method called when the data on the observing object has changed.
     * @param data the data.
     * @param reason the reason why the onChanged method was called.
     * */
    fun onChanged(data: T, reason: Reason)

}