package com.example.imagelistapp.main_list.presentation.view

import androidx.recyclerview.widget.DiffUtil
import com.example.imagelistapp.main_list.domain.entity.Image

class DiffUtilCallback(
    private val oldList: List<Image>,
    private val newList: List<Image>
) : DiffUtil.Callback() {
    override fun getOldListSize(): Int {
        return oldList.size
    }

    override fun getNewListSize(): Int {
        return newList.size
    }

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition].id == newList[newItemPosition].id
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return when {
            oldList[oldItemPosition].id == newList[newItemPosition].id &&
                    oldList[oldItemPosition].isFavorite == newList[newItemPosition].isFavorite -> true

            else -> false
        }
    }
}