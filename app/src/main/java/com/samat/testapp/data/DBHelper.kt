package com.samat.testapp.data

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteException
import android.database.sqlite.SQLiteOpenHelper
import android.provider.BaseColumns
import com.samat.testapp.data.FeedReaderContract.FeedEntry
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.io.InputStream

class DBHelper(private val context: Context) :
    SQLiteOpenHelper(context, DB_NAME, null, DB_VERSION) {

    private var database: SQLiteDatabase? = null
    private val dbPath = "${context.applicationInfo.dataDir}/databases"
    private val assetPath = "$ASSET_DB_PATH/$DB_NAME"
    private var isInitializing = false

    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(SQL_CREATE_ENTRIES)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        // This database is only a cache for online data, so its upgrade policy is
        // to simply to discard the data and start over
        db.execSQL(SQL_DELETE_ENTRIES)
        onCreate(db)
    }

    @Synchronized
    override fun getWritableDatabase(): SQLiteDatabase? {
        val curDb = database
        if (curDb != null && curDb.isOpen && !curDb.isReadOnly) {
            return curDb
        }

        if (isInitializing) {
            throw IllegalStateException("getWritableDatabase called recursively")
        }

        var db: SQLiteDatabase? = null
        var success = false
        try {
            isInitializing = true
            db = createOrOpenDatabase()

            if (db != null) {
                db.beginTransaction()
                try {
                    onCreate(db)
                    db.setTransactionSuccessful()
                } finally {
                    db.endTransaction()
                }
            }

            onOpen(db)
            success = true
            return db
        } finally {
            isInitializing = false
            if (success) {
                val oldDb = database
                if (oldDb != null) {
                    try {
                        oldDb.close()
                    } catch (_: Exception) {

                    }
                }
                database = db
            } else {
                db?.close()
            }
        }
    }

    private fun createOrOpenDatabase(): SQLiteDatabase? {
        var db: SQLiteDatabase? = null
        val file = File("$dbPath/$DB_NAME")
        if (file.exists()) {
            db = returnDatabase()
        }

        return if (db != null) {
            db
        } else {
            // database does not exist, copy it from assets and return it
            copyDatabaseFromAssets()
            returnDatabase()
        }
    }

    private fun copyDatabaseFromAssets() {
        val path = assetPath
        val dest = "$dbPath/$DB_NAME"
        val inputStream: InputStream

        try {
            inputStream = context.assets.open(path)
        } catch (e: IOException) {
            throw IOException("Missing $path file in assets!")
        }

        try {
            val file = File("$dbPath/")
            if (!file.exists()) {
                file.mkdir()
            }
            val outputStream = FileOutputStream(dest)
            val buffer = ByteArray(1024)
            var length: Int
            while (inputStream.read(buffer).also { length = it } > 0) {
                outputStream.write(buffer, 0, length)
            }
            outputStream.flush()
            outputStream.close()
            inputStream.close()
        } catch (e: IOException) {
            throw IOException("Unable to write $dest to data directory")
        }
    }

    private fun returnDatabase(): SQLiteDatabase? {
        return try {
            SQLiteDatabase.openDatabase(
                "$dbPath/$DB_NAME",
                null,
                SQLiteDatabase.OPEN_READWRITE
            )
        } catch (e: SQLiteException) {
            null
        }
    }

    @Synchronized
    override fun close() {
        if (isInitializing) throw IllegalStateException("Closed during initialization")

        val db = database
        if (db != null && db.isOpen) {
            db.close()
            database = null
        }
    }

    companion object {
        // If you change the database schema, you must increment the database version.
        private const val DB_NAME = "test_db.sqlite"
        private const val DB_VERSION = 1
        private const val ASSET_DB_PATH = "databases"

        private const val SQL_CREATE_ENTRIES =
            "CREATE TABLE ${FeedEntry.TABLE_NAME} (" +
                    "${BaseColumns._ID} INTEGER PRIMARY KEY," +
                    "${FeedEntry.COLUMN_NAME} TEXT," +
                    "${FeedEntry.COLUMN_PARENT_ID} INTEGER)"

        private const val SQL_DELETE_ENTRIES = "DROP TABLE IF EXISTS ${FeedEntry.TABLE_NAME}"
    }
}