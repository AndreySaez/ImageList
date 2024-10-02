package com.example.imagelistapp.main_list.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import javax.inject.Inject
import javax.inject.Provider

class ImageListViewModelFactory @Inject constructor(
    private val viewModelProvider: Provider<ImageListViewModel>
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        @Suppress("UNCHECKED_CAST")
        return viewModelProvider.get() as? T
            ?: throw IllegalStateException("Can't find viewModel")
    }
}