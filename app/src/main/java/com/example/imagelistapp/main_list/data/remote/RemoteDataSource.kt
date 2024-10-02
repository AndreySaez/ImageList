package com.example.imagelistapp.main_list.data.remote

import com.example.imagelistapp.main_list.data.DataSource
import com.example.imagelistapp.main_list.data.ImageMapper
import com.example.imagelistapp.main_list.domain.entity.Image
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class RemoteDataSource @Inject constructor(
    private val apiInterface: ApiInterface,
    private val mapper: ImageMapper
) : DataSource {
    override suspend fun getImages(page: Int, limit: Int): List<Image> =
        withContext(Dispatchers.IO) {
            apiInterface.getImages(
                page = page.toString(),
                limit = limit.toString()
            ).map(mapper::toImage)
        }
}