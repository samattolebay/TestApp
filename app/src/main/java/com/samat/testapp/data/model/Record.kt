package com.samat.testapp.data.model

import androidx.room.*

@Entity(
    tableName = "tt",
    indices = [Index(value = ["_parent_id"], name = "par_key")],
    foreignKeys = [ForeignKey(Record::class, ["_id"], childColumns = ["_parent_id"])]
)
data class Record(
    @PrimaryKey @ColumnInfo(name = "_id") val id: Int?,
    @ColumnInfo(name = "_name") val name: String?,
    @ColumnInfo(name = "_parent_id") val parentId: Int?
)
