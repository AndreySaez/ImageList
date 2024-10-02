package com.example.imagelistapp.main_list.data

import com.example.imagelistapp.main_list.domain.Repository
import com.example.imagelistapp.main_list.domain.entity.Image
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class RepositoryImpl(
    private val dataSource: DataSource
) : Repository {
    override suspend fun getImages(page: Int, limit: Int): Result<List<Image>> =
        withContext(Dispatchers.IO) {
            runCatching {
                dataSource.getImages(page = page, limit = limit)
            }
        }

}