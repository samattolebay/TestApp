package com.samat.testapp.data

import androidx.lifecycle.asLiveData
import com.samat.testapp.data.db.RecordDao

class AppRepository(
    private val dao: RecordDao,
) {

    fun getAllRecords() = dao.getAll().asLiveData()
}
