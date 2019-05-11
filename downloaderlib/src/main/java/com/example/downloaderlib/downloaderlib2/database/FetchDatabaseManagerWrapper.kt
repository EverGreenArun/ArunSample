package com.example.downloaderlib.downloaderlib2.database

import com.example.downloaderlib.downloaderlib2.PrioritySort
import com.example.downloaderlib.downloaderlib2.Status
import com.example.downloaderlib.downloaderlib.Extras

class FetchDatabaseManagerWrapper(private val fetchDatabaseManager: FetchDatabaseManager): FetchDatabaseManager {

    override val isClosed: Boolean
        get() {
            return synchronized(fetchDatabaseManager) {
                fetchDatabaseManager.isClosed
            }
        }

    override var delegate: FetchDatabaseManager.Delegate?
        get() {
            return synchronized(fetchDatabaseManager) {
                fetchDatabaseManager.delegate
            }
        }
        set(value) {
            synchronized(fetchDatabaseManager) {
                fetchDatabaseManager.delegate = value
            }
        }

    override fun insert(downloadInfo: DownloadInfo): Pair<DownloadInfo, Boolean> {
        return synchronized(fetchDatabaseManager) {
            fetchDatabaseManager.insert(downloadInfo)
        }
    }

    override fun insert(downloadInfoList: List<DownloadInfo>): List<Pair<DownloadInfo, Boolean>> {
        return synchronized(fetchDatabaseManager) {
            fetchDatabaseManager.insert(downloadInfoList)
        }
    }

    override fun delete(downloadInfo: DownloadInfo) {
        synchronized(fetchDatabaseManager) {
            fetchDatabaseManager.delete(downloadInfo)
        }
    }

    override fun delete(downloadInfoList: List<DownloadInfo>) {
        synchronized(fetchDatabaseManager) {
            fetchDatabaseManager.delete(downloadInfoList)
        }
    }

    override fun deleteAll() {
        synchronized(fetchDatabaseManager) {
            fetchDatabaseManager.deleteAll()
        }
    }

    override fun update(downloadInfo: DownloadInfo) {
        synchronized(fetchDatabaseManager) {
            fetchDatabaseManager.update(downloadInfo)
        }
    }

    override fun update(downloadInfoList: List<DownloadInfo>) {
        return synchronized(fetchDatabaseManager) {
            fetchDatabaseManager.update(downloadInfoList)
        }
    }

    override fun updateFileBytesInfoAndStatusOnly(downloadInfo: DownloadInfo) {
        return synchronized(fetchDatabaseManager) {
            fetchDatabaseManager.updateFileBytesInfoAndStatusOnly(downloadInfo)
        }
    }

    override fun get(): List<DownloadInfo> {
        return synchronized(fetchDatabaseManager) {
            fetchDatabaseManager.get()
        }
    }

    override fun get(id: Int): DownloadInfo? {
        return synchronized(fetchDatabaseManager) {
            fetchDatabaseManager.get(id)
        }
    }

    override fun get(ids: List<Int>): List<DownloadInfo?> {
        return synchronized(fetchDatabaseManager) {
            fetchDatabaseManager.get(ids)
        }
    }

    override fun getByFile(file: String): DownloadInfo? {
        return synchronized(fetchDatabaseManager) {
            fetchDatabaseManager.getByFile(file)
        }
    }

    override fun getByStatus(status: Status): List<DownloadInfo> {
        return synchronized(fetchDatabaseManager) {
            fetchDatabaseManager.getByStatus(status)
        }
    }

    override fun getByGroup(group: Int): List<DownloadInfo> {
        return synchronized(fetchDatabaseManager) {
            fetchDatabaseManager.getByGroup(group)
        }
    }

    override fun getDownloadsInGroupWithStatus(groupId: Int, statuses: List<Status>): List<DownloadInfo> {
        return synchronized(fetchDatabaseManager) {
            fetchDatabaseManager.getDownloadsInGroupWithStatus(groupId, statuses)
        }
    }

    override fun getDownloadsByRequestIdentifier(identifier: Long): List<DownloadInfo> {
        return synchronized(fetchDatabaseManager) {
            fetchDatabaseManager.getDownloadsByRequestIdentifier(identifier)
        }
    }

    override fun getPendingDownloadsSorted(prioritySort: PrioritySort): List<DownloadInfo> {
        return synchronized(fetchDatabaseManager) {
            fetchDatabaseManager.getPendingDownloadsSorted(prioritySort)
        }
    }

    override fun sanitizeOnFirstEntry() {
       synchronized(fetchDatabaseManager) {
           fetchDatabaseManager.sanitizeOnFirstEntry()
       }
    }

    override fun updateExtras(id: Int, extras: Extras): DownloadInfo? {
       return synchronized(fetchDatabaseManager) {
           fetchDatabaseManager.updateExtras(id, extras)
       }
    }

    override fun getPendingCount(includeAddedDownloads: Boolean): Long {
        return synchronized(fetchDatabaseManager) {
            fetchDatabaseManager.getPendingCount(includeAddedDownloads)
        }
    }

    override fun close() {
        synchronized(fetchDatabaseManager) {
            fetchDatabaseManager.close()
        }
    }

}