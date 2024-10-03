package com.example.imagelistapp.main_list.data.remote

import com.google.gson.annotations.SerializedName

data class ImageResponse(
    @SerializedName("id") val id: String,
    @SerializedName("download_url") val url: String,

)