package com.samat.testapp.ui.main

import android.content.ContentValues
import android.provider.BaseColumns
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.samat.testapp.data.DBHelper
import com.samat.testapp.data.FeedReaderContract.FeedEntry
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainViewModel(private val dbHelper: DBHelper) : ViewModel() {

    fun insertData(id: Int, name: String, parentId: Int) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                // Gets the data repository in write mode
                val db = dbHelper.writableDatabase

                // Create a new map of values, where column names are the keys
                val values = ContentValues().apply {
                    put(BaseColumns._ID, id)
                    put(FeedEntry.COLUMN_NAME, name)
                    put(FeedEntry.COLUMN_PARENT_ID, parentId)
                }

                // Insert the new row, returning the primary key value of the new row
                val newRowId = db?.insert(FeedEntry.TABLE_NAME, null, values)
            }
        }
    }
}
