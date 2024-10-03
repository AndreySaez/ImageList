package com.example.imagelistapp.main_list.data.local

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides

@Module
class RoomModule {

    @Provides
    fun provideDataBase(context: Context) = Room.databaseBuilder(
        context = context,
        ImageRoomDataBase::class.java,
        "image_list_database"
    ).build()

    @Provides
    fun provideDao(dataBase: ImageRoomDataBase): ImageListDao = dataBase.imageListDao()
}