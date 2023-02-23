package com.samat.testapp.data.db

import androidx.room.*
import com.samat.testapp.data.model.Record
import kotlinx.coroutines.flow.Flow

@Dao
interface RecordDao {

    @Query("SELECT * FROM records")
    fun getAll(): Flow<List<Record>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(vararg records: Record)

    @Delete
    suspend fun deleteAll(record: Record)
}