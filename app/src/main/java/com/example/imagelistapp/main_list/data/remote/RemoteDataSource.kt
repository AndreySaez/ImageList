package com.example.imagelistapp.main_list.data.remote

import com.example.imagelistapp.main_list.data.DataSource
import com.example.imagelistapp.main_list.data.local.ImageListDao
import com.example.imagelistapp.main_list.data.mappers.RemoteToImageMapper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class RemoteDataSource @Inject constructor(
    private val apiInterface: ApiInterface,
    private val mapper: RemoteToImageMapper,
    private val imageListDao: ImageListDao
) : DataSource {
    override suspend fun getImages(page: Int, limit: Int) = withContext(Dispatchers.IO) {
        val remoteImageList = apiInterface.getImages(
            page = page.toString(),
            limit = limit.toString()
        )
        val ids = remoteImageList.map { it.id }
        val favoriteIds = imageListDao.matchFaveIds(ids).map { it }.toSet()
        remoteImageList.map {
            mapper.toImage(
                it,
                favoriteIds
            )
        }
    }
}