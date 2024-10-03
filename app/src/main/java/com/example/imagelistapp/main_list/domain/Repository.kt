package com.example.imagelistapp.main_list.domain

import com.example.imagelistapp.main_list.domain.entity.Image
import com.example.imagelistapp.main_list.domain.entity.LocalImage

interface Repository {
    suspend fun getImages(page: Int, limit: Int): Result<List<Image>>
    suspend fun addImageToFavorite(image: LocalImage)
    suspend fun removeImageFromFavorite(image: LocalImage)
}