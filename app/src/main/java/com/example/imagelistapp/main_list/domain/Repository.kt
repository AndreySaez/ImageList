package com.example.imagelistapp.main_list.domain

import com.example.imagelistapp.main_list.domain.entity.Image

interface Repository {
    suspend fun getImages(page: Int, limit: Int): Result<List<Image>>
}