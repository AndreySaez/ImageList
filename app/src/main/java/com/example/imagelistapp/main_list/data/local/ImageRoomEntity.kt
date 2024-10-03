package com.example.imagelistapp.main_list.data.local

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.imagelistapp.main_list.data.local.ImageRoomEntity.Companion.TABLE_NAME

@Entity(tableName = TABLE_NAME)
data class ImageRoomEntity(
    @PrimaryKey @ColumnInfo(name = "id")
    val id: String,

    @ColumnInfo(name = "bitmap")
    val bitmap: ByteArray,
) {
    companion object {
        const val TABLE_NAME = "image_list_entities_table"
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as ImageRoomEntity

        if (id != other.id) return false
        if (!bitmap.contentEquals(other.bitmap)) return false

        return true
    }

    override fun hashCode(): Int {
        var result = id.hashCode()
        result = 31 * result + bitmap.contentHashCode()
        return result
    }
}