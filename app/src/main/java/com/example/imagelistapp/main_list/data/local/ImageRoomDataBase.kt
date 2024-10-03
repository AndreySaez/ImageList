package com.example.imagelistapp.main_list.data.local

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [ImageRoomEntity::class], version = 1, exportSchema = true)
abstract class ImageRoomDataBase : RoomDatabase() {
    abstract fun imageListDao(): ImageListDao
}