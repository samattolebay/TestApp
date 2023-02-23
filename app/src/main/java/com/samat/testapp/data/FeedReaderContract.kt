package com.samat.testapp.data

import android.provider.BaseColumns

object FeedReaderContract {
    // Table contents are grouped together in an anonymous object.
    object FeedEntry : BaseColumns {
        const val TABLE_NAME = "tt"
        const val COLUMN_NAME = "title"
        const val COLUMN_PARENT_ID = "parent_id"
    }
}