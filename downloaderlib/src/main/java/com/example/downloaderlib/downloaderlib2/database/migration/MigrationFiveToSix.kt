package com.example.downloaderlib.downloaderlib2.database.migration

import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.downloaderlib.downloaderlib2.database.DownloadDatabase
import com.example.downloaderlib.downloaderlib2.util.EMPTY_JSON_OBJECT_STRING

class MigrationFiveToSix : Migration(5, 6) {

    override fun migrate(database: SupportSQLiteDatabase) {
        database.execSQL("ALTER TABLE '${DownloadDatabase.TABLE_NAME}' "
                + "ADD COLUMN '${DownloadDatabase.COLUMN_EXTRAS}' TEXT NOT NULL DEFAULT '$EMPTY_JSON_OBJECT_STRING'")
    }

}