package com.example.imagelistapp.main_list.presentation.view

import android.widget.ImageView
import coil.load
import com.example.imagelistapp.R
import com.example.imagelistapp.main_list.domain.entity.Image
import com.example.imagelistapp.main_list.domain.entity.RemoteImage

fun ImageView.loadImage(image: Image) {
    val source = when (image) {
        is RemoteImage -> image.url
        //Потом будет LocalImage
    }
    load(source) {
        placeholder(R.drawable.error_image_placeholder)
        fallback(R.drawable.error_image_placeholder)
        error(R.drawable.error_image_placeholder)
    }
}