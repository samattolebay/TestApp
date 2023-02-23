package com.samat.testapp.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.samat.testapp.data.model.Record

@Database(entities = [Record::class], version = 1)
abstract class AppDatabase : RoomDatabase() {

    abstract fun recordDao(): RecordDao
}
