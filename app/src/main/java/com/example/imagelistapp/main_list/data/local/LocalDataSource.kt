package com.example.imagelistapp.main_list.data.local

import com.example.imagelistapp.main_list.data.DataSource
import com.example.imagelistapp.main_list.data.mappers.LocalToImageMapper
import com.example.imagelistapp.main_list.domain.entity.Image
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class LocalDataSource @Inject constructor(
    private val imageListDao: ImageListDao,
    private val mapper: LocalToImageMapper
) : DataSource {
    override suspend fun getImages(page: Int, limit: Int): List<Image> =
        withContext(Dispatchers.IO) {
            imageListDao.getFavoritesImageList(offset = page * limit, limit = limit)
                .map(mapper::toImage)
        }
}