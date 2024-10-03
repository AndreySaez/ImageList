package com.example.imagelistapp.main_list.presentation.viewmodel

import com.example.imagelistapp.main_list.domain.entity.Image

sealed interface ImageListActions {
    data object OnScrolledToEnd : ImageListActions
    data object Reload : ImageListActions
    data class FavoriteIconClicked(val image: Image, val bitmap: ByteArray) : ImageListActions {
        override fun equals(other: Any?): Boolean {
            if (this === other) return true
            if (javaClass != other?.javaClass) return false

            other as FavoriteIconClicked

            if (image != other.image) return false
            if (!bitmap.contentEquals(other.bitmap)) return false

            return true
        }

        override fun hashCode(): Int {
            var result = image.hashCode()
            result = 31 * result + bitmap.contentHashCode()
            return result
        }
    }
}