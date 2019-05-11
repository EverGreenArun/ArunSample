package com.example.downloaderlib.downloaderlib2.database

import androidx.room.*
import com.example.downloaderlib.downloaderlib2.Status
import com.example.downloaderlib.downloaderlib2.database.DownloadDatabase.Companion.COLUMN_CREATED
import com.example.downloaderlib.downloaderlib2.database.DownloadDatabase.Companion.COLUMN_FILE
import com.example.downloaderlib.downloaderlib2.database.DownloadDatabase.Companion.COLUMN_GROUP
import com.example.downloaderlib.downloaderlib2.database.DownloadDatabase.Companion.COLUMN_ID
import com.example.downloaderlib.downloaderlib2.database.DownloadDatabase.Companion.COLUMN_IDENTIFIER
import com.example.downloaderlib.downloaderlib2.database.DownloadDatabase.Companion.COLUMN_PRIORITY
import com.example.downloaderlib.downloaderlib2.database.DownloadDatabase.Companion.COLUMN_STATUS
import com.example.downloaderlib.downloaderlib2.database.DownloadDatabase.Companion.TABLE_NAME
import com.example.downloaderlib.downloaderlib2.database.DownloadInfo


@Dao
interface DownloadDao {

    @Insert(onConflict = OnConflictStrategy.ABORT)
    fun insert(downloadInfo: DownloadInfo): Long

    @Insert(onConflict = OnConflictStrategy.ABORT)
    fun insert(downloadInfoList: List<DownloadInfo>): List<Long>

    @Delete
    fun delete(downloadInfo: DownloadInfo)

    @Delete
    fun delete(downloadInfoList: List<DownloadInfo>)

    @Query("DELETE FROM $TABLE_NAME")
    fun deleteAll()

    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun update(download: DownloadInfo)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun update(downloadInfoList: List<DownloadInfo>)

    @Query("SELECT * FROM $TABLE_NAME")
    fun get(): List<DownloadInfo>

    @Query("SELECT * FROM $TABLE_NAME WHERE $COLUMN_ID = :id")
    fun get(id: Int): DownloadInfo?

    @Query("SELECT * FROM $TABLE_NAME WHERE $COLUMN_ID IN (:ids)")
    fun get(ids: List<Int>): List<DownloadInfo>

    @Query("SELECT * FROM $TABLE_NAME WHERE $COLUMN_FILE = :file")
    fun getByFile(file: String): DownloadInfo?

    @Query("SELECT * FROM $TABLE_NAME WHERE $COLUMN_STATUS = :status")
    fun getByStatus(status: Status): List<DownloadInfo>

    @Query("SELECT * FROM $TABLE_NAME WHERE $COLUMN_GROUP = :group")
    fun getByGroup(group: Int): List<DownloadInfo>

    @Query("SELECT * FROM $TABLE_NAME WHERE $COLUMN_GROUP = :group AND $COLUMN_STATUS IN (:statuses)")
    fun getByGroupWithStatus(group: Int, statuses: MutableList<Status>): List<DownloadInfo>

    @Query("SELECT * FROM $TABLE_NAME WHERE $COLUMN_STATUS = :status ORDER BY $COLUMN_PRIORITY DESC, $COLUMN_CREATED ASC")
    fun getPendingDownloadsSorted(status: Status): List<DownloadInfo>

    @Query("SELECT * FROM $TABLE_NAME WHERE $COLUMN_STATUS = :status ORDER BY $COLUMN_PRIORITY DESC, $COLUMN_CREATED DESC")
    fun getPendingDownloadsSortedDesc(status: Status): List<DownloadInfo>

    @Query("SELECT * FROM $TABLE_NAME WHERE $COLUMN_IDENTIFIER = :identifier")
    fun getDownloadsByRequestIdentifier(identifier: Long): List<DownloadInfo>

}