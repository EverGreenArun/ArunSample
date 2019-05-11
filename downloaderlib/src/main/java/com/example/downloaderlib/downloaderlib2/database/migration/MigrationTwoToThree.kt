package com.example.downloaderlib.downloaderlib2.database.migration

import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.downloaderlib.downloaderlib2.EnqueueAction
import com.example.downloaderlib.downloaderlib2.database.DownloadDatabase

class MigrationTwoToThree : Migration(2, 3) {

    override fun migrate(database: SupportSQLiteDatabase) {
        database.execSQL("ALTER TABLE '${DownloadDatabase.TABLE_NAME}' "
                + "ADD COLUMN '${DownloadDatabase.COLUMN_ENQUEUE_ACTION}' INTEGER NOT NULL DEFAULT ${EnqueueAction.REPLACE_EXISTING.value}")
    }

}