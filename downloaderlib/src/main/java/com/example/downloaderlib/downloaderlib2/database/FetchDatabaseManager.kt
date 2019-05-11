package com.example.downloaderlib.downloaderlib2.database

import com.example.downloaderlib.downloaderlib2.PrioritySort
import com.example.downloaderlib.downloaderlib2.Status
import com.example.downloaderlib.downloaderlib.Extras
import java.io.Closeable

/**
 * This interface can be implemented by a class to create a custom FetchDatabaseManager.
 * The default Fetch Database Manager is FetchDatabaseManagerImpl which uses sqlite/room
 * to store download information. All methods and fields will be called on Fetch's background thread.
 * */
interface FetchDatabaseManager : Closeable {

    /**
     * Checks if the database is closed.
     * */
    val isClosed: Boolean

    /**
     * Delegate used by Fetch to delete temporary files used to assist the parallel downloader.
     * This field is set by the Fetch Builder directly. Your implemention should set this to null
     * by default.
     * */
    var delegate: FetchDatabaseManager.Delegate?

    /**
     * Inserts a new download into the database.
     * @param downloadInfo object containing the download information.
     * @return a pair with the store or updated download information and a boolean.
     * indicated if the insert was a success.
     * */
    fun insert(downloadInfo: DownloadInfo): Pair<DownloadInfo, Boolean>

    /**
     * Inserts a list of downloads into the database.
     * @param downloadInfoList list objects containing the download information.
     * @return a list of pairs with the store or updated download information and a boolean.
     * indicated if the insert for each download was a success.
     * */
    fun insert(downloadInfoList: List<DownloadInfo>): List<Pair<DownloadInfo, Boolean>>

    /**
     * Deletes a download from the database.
     * @param downloadInfo object containing the download information.
     * */
    fun delete(downloadInfo: DownloadInfo)

    /**
     * Deletes a list of downloads from the database.
     * @param downloadInfoList list of objects containing the download information.
     * */
    fun delete(downloadInfoList: List<DownloadInfo>)

    /**
    * Deletes all downloads in the database.
    * */
    fun deleteAll()

    /**
     * Updates a download in the database.
     * @param downloadInfo the download information.
     * */
    fun update(downloadInfo: DownloadInfo)

    /**
     * Updates a list of downloads in the database.
     * @param downloadInfoList list of downloads.
     * */
    fun update(downloadInfoList: List<DownloadInfo>)

    /**
     * Updates only the file bytes and status of a download in the database.
     * @param downloadInfo the download information.
     * */
    fun updateFileBytesInfoAndStatusOnly(downloadInfo: DownloadInfo)

    /**
     * Gets a list of all the downloads in the database.
     * */
    fun get(): List<DownloadInfo>

    /**
     * Gets a download from the database by id if it exists.
     * @param the download id.
     * @return the download or null.
     * */
    fun get(id: Int): DownloadInfo?

    /**
     * Gets a list of downloads from the database by id if it exists.
     * @param ids the list ids the database will search against.
     * @return the list of downloads for each of ids in order. If a download
     * does not exist for an id null will be returned.
     * */
    fun get(ids: List<Int>): List<DownloadInfo?>

    /**
     * Gets a download by file name if it exists.
     * @param file the file
     * @return the download if it exists.
     * */
    fun getByFile(file: String): DownloadInfo?

    /**
     * Get all downloads by the specified status.
     * @param status the query status.
     * @return all downloads in the database with the specified status.
     * */
    fun getByStatus(status: Status): List<DownloadInfo>

    /**
     * Gets all downloads that belongs to the specified group.
     * @param group the group id
     * @return list of downloads belonging to the group
     * */
    fun getByGroup(group: Int): List<DownloadInfo>

    /**
     * Gets all downloads in the specified group with the specified statuses.
     * @param groupId the group id
     * @param statuses the list of statues to query against.
     * @return list of downloads matching the query.
     * */
    fun getDownloadsInGroupWithStatus(groupId: Int, statuses: List<Status>): List<DownloadInfo>

    /**
     * Get a list of downloads by the specified request identifier.
     * @param identifier the request identifier
     * @return list of downloads matching the query.
     * */
    fun getDownloadsByRequestIdentifier(identifier: Long): List<DownloadInfo>

    /**
     * Get a list of downloads that are pending(status = Queued) for download in sorted order by(priority(DESC), created(ASC)
     * @param prioritySort the sort priority for created. Default is ASC
     * @return list of pending downloads in sorted order.
     * */
    fun getPendingDownloadsSorted(prioritySort: PrioritySort): List<DownloadInfo>

    /**
     * Called when the first instance of Fetch for a namespace is created. Use this method
     * to ensure the database is clean and up to date.
     * Note: Applications may quit unexpectedly. Use this method to update the status of any downloads
     * with a status of Downloading to Queued. Otherwise these downloads will never continue.
     * */
    fun sanitizeOnFirstEntry()

    /**
     * Updates the extras on a download.
     * @param id the download id.
     * @param extras the new extras that will replace the existing extras on the download.
     * */
    fun updateExtras(id: Int, extras: Extras): DownloadInfo?

    /**
     * Gets the count/sum of all downloads with the status of Queued and Downloading combined.
     * @param includeAddedDownloads if to include downloads with the status of Added.
     * Added downloads are not considered pending by default.
     * @return the pending download count.
     * */
    fun getPendingCount(includeAddedDownloads: Boolean): Long

    /**
     * Interface used for the DownloadManager's delegate.
     * */
    interface Delegate {

        /**
         * Deletes all associated temp files used to perform a download for a download.
         * @param downloadInfo download information.
         * */
        fun deleteTempFilesForDownload(downloadInfo: DownloadInfo)

    }

}