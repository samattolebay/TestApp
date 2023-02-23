package com.samat.testapp.ui.main

import androidx.lifecycle.*
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import com.samat.testapp.data.AppRepository
import com.samat.testapp.data.paging.RecordPagingSource

class MainViewModel(private val repository: AppRepository, parentId: Int) : ViewModel() {

    val records = Pager(PagingConfig(PAGE_SIZE)) {
        RecordPagingSource(repository, parentId)
    }
        .flow
        .cachedIn(viewModelScope)

    // Define ViewModel factory in a companion object
    companion object {
        private const val PAGE_SIZE = 20

        fun provideFactory(
            repository: AppRepository,
            parentId: Int
        ): AbstractSavedStateViewModelFactory =
            object : AbstractSavedStateViewModelFactory() {
                @Suppress("UNCHECKED_CAST")
                override fun <T : ViewModel> create(
                    key: String,
                    modelClass: Class<T>,
                    handle: SavedStateHandle
                ): T {
                    return MainViewModel(repository, parentId) as T
                }
            }
    }
}
