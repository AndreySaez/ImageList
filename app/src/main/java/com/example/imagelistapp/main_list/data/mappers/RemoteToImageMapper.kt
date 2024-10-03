package com.example.imagelistapp.main_list.data.mappers

import com.example.imagelistapp.main_list.data.remote.ImageResponse
import com.example.imagelistapp.main_list.domain.entity.RemoteImage
import javax.inject.Inject

class RemoteToImageMapper @Inject constructor() {
    fun toImage(imageResponse: ImageResponse, faveIds: Set<String>) = RemoteImage(
        id = imageResponse.id,
        url = "https://picsum.photos/id/${imageResponse.id}/200/200",
        isFavorite = faveIds.contains(imageResponse.id)
    )

}