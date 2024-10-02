package com.example.imagelistapp.main_list.data

import com.example.imagelistapp.main_list.domain.entity.Image

interface DataSource {
    suspend fun getImages(page: Int, limit: Int): List<Image>
}