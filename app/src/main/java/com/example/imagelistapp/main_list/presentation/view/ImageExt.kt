package com.example.imagelistapp.main_list.presentation.view

import android.graphics.Bitmap
import android.widget.ImageView
import androidx.core.graphics.drawable.toBitmap
import coil.load
import com.example.imagelistapp.R
import com.example.imagelistapp.main_list.domain.entity.Image
import com.example.imagelistapp.main_list.domain.entity.LocalImage
import com.example.imagelistapp.main_list.domain.entity.RemoteImage
import java.io.ByteArrayOutputStream


fun ImageView.loadImage(image: Image) {
    val source = when (image) {
        is RemoteImage -> image.url
        is LocalImage -> image.bitmap
    }
    load(source) {
        placeholder(R.drawable.error_image_placeholder)
        fallback(R.drawable.error_image_placeholder)
        error(R.drawable.error_image_placeholder)
    }
}

fun ImageView.getBitmapArray(): ByteArray {
    val bitmap = this.drawable.toBitmap()
    val byteArray = ByteArrayOutputStream().use { stream ->
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream)
        stream.toByteArray()
    }
    return byteArray
}