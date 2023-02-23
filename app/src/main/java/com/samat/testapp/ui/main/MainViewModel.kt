package com.samat.testapp.ui.main

import androidx.lifecycle.AbstractSavedStateViewModelFactory
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.samat.testapp.data.AppRepository

class MainViewModel(repository: AppRepository) : ViewModel() {

    val records = repository.getAllRecords()


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
