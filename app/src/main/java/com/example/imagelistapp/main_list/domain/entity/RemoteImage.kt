package com.example.imagelistapp.main_list.domain.entity

data class RemoteImage(
    override val id: String,
    override val isFavorite: Boolean,
    val url: String,
) : Image