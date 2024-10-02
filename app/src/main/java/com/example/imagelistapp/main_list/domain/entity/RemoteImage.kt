package com.example.imagelistapp.main_list.domain.entity

data class RemoteImage(
    override val id: String,
    val url: String,
) : Image