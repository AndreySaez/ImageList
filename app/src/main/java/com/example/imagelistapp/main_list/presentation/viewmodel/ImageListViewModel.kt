package com.example.imagelistapp.main_list.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.imagelistapp.main_list.domain.Repository
import com.example.imagelistapp.main_list.domain.entity.Image
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class ImageListViewModel @Inject constructor(
    private val repository: Repository
) : ViewModel() {
    val state get() = _state.asStateFlow()
    private val _state = MutableStateFlow<ImageListState>(ImageListState.IsEmpty)

    private var nextPage = INITIAL_PAGE
    private var hasMoreItems = true
    private var loadingJob: Job? = null

    init {
        loadInitial()
    }

    fun onAction(action: Action) {
        when (action) {
            Action.OnScrolledToEnd -> loadNext()
            Action.Reload -> loadInitial()
        }

    }

    private fun loadNext() {
        if (loadingJob?.isActive == true || !hasMoreItems) return
        loadingJob = viewModelScope.launch { load() }
    }

    private fun loadInitial() {
        if (loadingJob?.isActive == true) loadingJob?.cancel()
        loadingJob = viewModelScope.launch {
            setState(ImageListState.IsLoading)
            resetPagination()
            load()
        }
    }

    private fun resetPagination() {
        nextPage = INITIAL_PAGE
        hasMoreItems = true
    }

    private suspend fun load() {
        val loadedImages = repository.getImages(
            page = nextPage,
            limit = PAGE_SIZE
        )
        if (loadedImages.isFailure) {
            handleLoadingError()
            return
        }
        checkPaginationState(loadedImages.getOrThrow())
        val images = mergeLoadedItems(loadedImages.getOrThrow())
        showListOrEmpty(images)
    }

    private fun showListOrEmpty(images: List<Image>) {
        if (images.isNotEmpty()) {
            ImageListState.ImageList(images, hasMoreItems, hasLoadingError = false)
        } else {
            ImageListState.IsEmpty
        }.also {
            setState(it)
        }
    }

    private fun mergeLoadedItems(loadedImages: List<Image>): List<Image> {
        val currentList = (state.value as? ImageListState.ImageList)?.list ?: emptyList()
        return currentList + loadedImages
    }

    private fun handleLoadingError() {
        val currentState = state.value
        if (currentState is ImageListState.ImageList) {
            currentState.copy(hasLoadingError = true)
        } else {
            ImageListState.IsError
        }.also {
            setState(it)
        }
    }

    private fun setState(state: ImageListState) {
        _state.value = state
    }

    private fun checkPaginationState(loadedImages: List<Image>) {
        if (loadedImages.size < PAGE_SIZE) {
            hasMoreItems = false
        } else {
            nextPage++
        }
    }

    companion object {
        const val PAGE_SIZE = 20
        const val INITIAL_PAGE = 1
    }
}

sealed interface ImageListState {
    data object IsEmpty : ImageListState
    data object IsLoading : ImageListState
    data object IsError : ImageListState
    data class ImageList(
        val list: List<Image>,
        val hasMoreItems: Boolean,
        val hasLoadingError: Boolean
    ) : ImageListState
}