package com.example.imagelistapp.main_list.data

import com.example.imagelistapp.main_list.data.remote.ImageResponse
import com.example.imagelistapp.main_list.domain.entity.RemoteImage
import javax.inject.Inject

class ImageMapper @Inject constructor() {
    fun toImage(imageResponse: ImageResponse) = RemoteImage(
        id = imageResponse.id,
        url = "https://picsum.photos/id/${imageResponse.id}/200/200"
    )
}