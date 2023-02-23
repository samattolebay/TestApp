package com.samat.testapp

import android.app.Application
import androidx.room.Room
import com.samat.testapp.data.AppRepository
import com.samat.testapp.data.db.AppDatabase
import kotlinx.coroutines.Dispatchers

class MyApp : Application() {

    lateinit var repository: AppRepository

    override fun onCreate() {
        super.onCreate()
        val db = Room.databaseBuilder(this, AppDatabase::class.java, "record-db")
            .createFromAsset("database/test_db.sqlite").build()
        val ioDispatcher = Dispatchers.IO

        repository = AppRepository(ioDispatcher, db.recordDao())
    }
}
