package com.samat.testapp.data

import androidx.lifecycle.asLiveData
import com.samat.testapp.data.db.RecordDao
import com.samat.testapp.data.model.Record
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext

class AppRepository(
    private val ioDispatcher: CoroutineDispatcher,
    private val dao: RecordDao,
) {

    fun getAllRecords() = dao.getAll().asLiveData()

    suspend fun getRecordsByParentId(parentId: Int, size: Int): List<Record> {
        return withContext(ioDispatcher) {
            dao.getRecordsByParentId(parentId, size)
        }
    }
}
