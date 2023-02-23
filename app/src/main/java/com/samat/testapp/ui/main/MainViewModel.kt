package com.samat.testapp.ui.main

import androidx.lifecycle.*
import com.samat.testapp.data.AppRepository
import com.samat.testapp.data.model.Record
import kotlinx.coroutines.launch

class MainViewModel(private val repository: AppRepository) : ViewModel() {

    private val _records = MutableLiveData<List<Record>>()
    val records: LiveData<List<Record>>
        get() = _records

    fun getRecordsByParentId(parentId: Int) {
        viewModelScope.launch {
            val result = repository.getRecordsByParentId(parentId)
            _records.value = result
        }
    }


    // Define ViewModel factory in a companion object
    companion object {
        fun provideFactory(repository: AppRepository): AbstractSavedStateViewModelFactory =
            object : AbstractSavedStateViewModelFactory() {
                @Suppress("UNCHECKED_CAST")
                override fun <T : ViewModel> create(
                    key: String,
                    modelClass: Class<T>,
                    handle: SavedStateHandle
                ): T {
                    return MainViewModel(repository) as T
                }
            }
    }
}
