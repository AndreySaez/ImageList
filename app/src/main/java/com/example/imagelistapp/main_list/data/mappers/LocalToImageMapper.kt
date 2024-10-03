package com.example.imagelistapp.main_list.data.mappers

import com.example.imagelistapp.main_list.data.local.ImageRoomEntity
import com.example.imagelistapp.main_list.domain.entity.Image
import com.example.imagelistapp.main_list.domain.entity.LocalImage
import javax.inject.Inject

class LocalToImageMapper @Inject constructor() {
    fun toImage(imageRoomEntity: ImageRoomEntity): Image = LocalImage(
        id = imageRoomEntity.id,
        bitmap = imageRoomEntity.bitmap
    )
}