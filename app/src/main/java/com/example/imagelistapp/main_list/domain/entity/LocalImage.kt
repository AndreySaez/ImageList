package com.example.imagelistapp.main_list.domain.entity

data class LocalImage(
    override val id: String,
    val bitmap: ByteArray,
    override val isFavorite: Boolean = true
) : Image {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as LocalImage

        if (id != other.id) return false
        if (!bitmap.contentEquals(other.bitmap)) return false
        if (isFavorite != other.isFavorite) return false

        return true
    }

    override fun hashCode(): Int {
        var result = id.hashCode()
        result = 31 * result + bitmap.contentHashCode()
        result = 31 * result + isFavorite.hashCode()
        return result
    }
}