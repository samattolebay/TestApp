package com.samat.testapp

import android.app.Application
import androidx.room.Room
import com.samat.testapp.data.AppRepository
import com.samat.testapp.data.db.AppDatabase

class MyApp : Application() {

    lateinit var repository: AppRepository

    override fun onCreate() {
        super.onCreate()
        val db = Room.databaseBuilder(this, AppDatabase::class.java, "record-db")
            .createFromAsset("database/test_db.sqlite").build()

        repository = AppRepository(db.recordDao())
    }
}
