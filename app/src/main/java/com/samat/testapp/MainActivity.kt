package com.samat.testapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.BaseColumns
import android.widget.Toast
import com.samat.testapp.data.DBHelper
import com.samat.testapp.data.FeedReaderContract.FeedEntry
import com.samat.testapp.ui.main.MainFragment

class MainActivity : AppCompatActivity() {

    private lateinit var dbHelper: DBHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, MainFragment.newInstance())
                .commitNow()
        }

        val dbHelper = DBHelper(this)
        val db = dbHelper.readableDatabase

        // Define a projection that specifies which columns from the database
        // you will actually use after this query.
        val projection =
            arrayOf(BaseColumns._ID, FeedEntry.COLUMN_NAME, FeedEntry.COLUMN_PARENT_ID)

//        // Filter results WHERE "title" = 'My Title'
//        val selection = "${FeedEntry.COLUMN_NAME} = ?"
//        val selectionArgs = arrayOf("My Title")

        // How you want the results sorted in the resulting Cursor
        val sortOrder = "${FeedEntry.COLUMN_PARENT_ID} DESC"

        val cursor = db.query(
            FeedEntry.TABLE_NAME,   // The table to query
            null,             // The array of columns to return (pass null to get all)
            null,              // The columns for the WHERE clause
            null,          // The values for the WHERE clause
            null,           // don't group the rows
            null,            // don't filter by row groups
            null,               // The sort order
            "10"
        )

        val itemIds = mutableListOf<Long>()
        with(cursor) {
            while (moveToNext()) {
                val itemId = getLong(getColumnIndexOrThrow(BaseColumns._ID))
                itemIds.add(itemId)
            }
        }
        println(itemIds)
        Toast.makeText(this, itemIds.joinToString(), Toast.LENGTH_SHORT).show()
        cursor.close()
    }

    override fun onDestroy() {
        dbHelper.close()
        super.onDestroy()
    }
}