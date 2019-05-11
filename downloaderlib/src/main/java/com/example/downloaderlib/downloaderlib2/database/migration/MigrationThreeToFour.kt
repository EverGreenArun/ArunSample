package com.example.downloaderlib.downloaderlib2.database.migration

import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.downloaderlib.downloaderlib2.database.DownloadDatabase
import com.example.downloaderlib.downloaderlib2.util.DEFAULT_UNIQUE_IDENTIFIER

class MigrationThreeToFour : Migration(3, 4) {

    override fun migrate(database: SupportSQLiteDatabase) {
        database.execSQL("ALTER TABLE '${DownloadDatabase.TABLE_NAME}' "
                + "ADD COLUMN '${DownloadDatabase.COLUMN_IDENTIFIER}' INTEGER NOT NULL DEFAULT $DEFAULT_UNIQUE_IDENTIFIER")
    }

}