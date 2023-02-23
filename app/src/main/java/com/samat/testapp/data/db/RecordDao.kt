package com.samat.testapp.data.db

import androidx.room.*
import com.samat.testapp.data.model.Record
import kotlinx.coroutines.flow.Flow

@Dao
interface RecordDao {

    @Query("SELECT * FROM tt")
    fun getAll(): Flow<List<Record>>

    @Query("SELECT * FROM tt WHERE _parent_id = :parentId")
    suspend fun getRecordsByParentId(parentId: Int): List<Record>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(vararg records: Record)

    @Delete
    suspend fun deleteAll(record: Record)
}
