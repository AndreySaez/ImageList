package com.example.imagelistapp.main_list.data

import com.example.imagelistapp.main_list.data.local.ImageListDao
import com.example.imagelistapp.main_list.data.local.ImageRoomEntity
import com.example.imagelistapp.main_list.domain.Repository
import com.example.imagelistapp.main_list.domain.entity.Image
import com.example.imagelistapp.main_list.domain.entity.LocalImage
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class RepositoryImpl(
    private val dataSource: DataSource,
    private val imageListDao: ImageListDao
) : Repository {
    override suspend fun getImages(page: Int, limit: Int): Result<List<Image>> =
        withContext(Dispatchers.IO) {
            runCatching {
                dataSource.getImages(page = page, limit = limit)
            }
        }

    override suspend fun addImageToFavorite(image: LocalImage) {
        withContext(Dispatchers.IO) {
            runCatching {
                imageListDao.addImageToFavorite(ImageRoomEntity(image.id, image.bitmap))
            }
        }
    }

    override suspend fun removeImageFromFavorite(image: LocalImage) {
        withContext(Dispatchers.IO) {
            runCatching {
                imageListDao.removeImageFromFavorite(ImageRoomEntity(image.id, image.bitmap))
            }
        }
    }


}