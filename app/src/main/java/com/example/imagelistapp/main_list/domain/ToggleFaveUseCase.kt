package com.example.imagelistapp.main_list.domain

import com.example.imagelistapp.main_list.domain.entity.Image
import com.example.imagelistapp.main_list.domain.entity.LocalImage
import javax.inject.Inject

class ToggleFaveUseCase @Inject constructor(
    private val repository: Repository
) {
    suspend fun toggleFave(image: Image, bitMap: ByteArray) {
        if (image.isFavorite) {
            repository.removeImageFromFavorite(LocalImage(image.id, bitMap))
        } else {
            repository.addImageToFavorite(LocalImage(image.id, bitMap))
        }
    }
}