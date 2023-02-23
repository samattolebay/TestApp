package com.samat.testapp.data.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.samat.testapp.data.AppRepository
import com.samat.testapp.data.model.Record
import java.io.IOException

class RecordPagingSource(private val repository: AppRepository, private val parentId: Int) :
    PagingSource<Int, Record>() {
    override fun getRefreshKey(state: PagingState<Int, Record>): Int? {
        // Try to find the page key of the closest page to anchorPosition, from
        // either the prevKey or the nextKey, but you need to handle nullability
        // here:
        //  * prevKey == null -> anchorPage is the first page.
        //  * nextKey == null -> anchorPage is the last page.
        //  * both prevKey and nextKey null -> anchorPage is the initial page, so
        //    just return null.
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Record> {
        try {
            // Start refresh at page 1 if undefined.
            val nextPageNumber = params.key ?: 1
            val response = repository.getRecordsByParentId(parentId, nextPageNumber)
            return LoadResult.Page(
                data = response,
                prevKey = null, // Only paging forward.
                nextKey = if (response.isEmpty()) null else nextPageNumber + 1
            )
        } catch (e: IOException) {
            // IOException for network failures.
            return LoadResult.Error(e)
        } catch (e: Exception) {
            // Other exceptions
            return LoadResult.Error(e)
        }
    }
}
