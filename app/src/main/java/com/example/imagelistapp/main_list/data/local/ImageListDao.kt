package com.example.imagelistapp.main_list.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface ImageListDao {
    @Query("SELECT * FROM ${ImageRoomEntity.TABLE_NAME} LIMIT :limit OFFSET :offset")
    suspend fun getFavoritesImageList(limit: Int, offset: Int): List<ImageRoomEntity>

    @Insert(entity = ImageRoomEntity::class, onConflict = OnConflictStrategy.REPLACE)
    suspend fun addImageToFavorite(image: ImageRoomEntity)

    @Delete(entity = ImageRoomEntity::class)
    suspend fun removeImageFromFavorite(image: ImageRoomEntity)

    @Query("SELECT id FROM ${ImageRoomEntity.TABLE_NAME} where id IN (:picIds)")
    fun matchFaveIds(picIds: List<String>): List<String>
}