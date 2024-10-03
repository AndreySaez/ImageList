package com.example.imagelistapp.main_list.domain.entity

sealed interface Image {
    val id: String
    val isFavorite: Boolean
}

fun Image.toggleFavorite(): Image {
    return when (this) {
        is RemoteImage -> {
            copy(isFavorite = !isFavorite)
        }

        is LocalImage -> {
            copy(isFavorite = !isFavorite)
        }
    }
}