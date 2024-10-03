package com.example.imagelistapp.main_list.presentation.view

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.imagelistapp.R
import com.example.imagelistapp.main_list.domain.entity.Image

class ImageListAdapter(
    private val faveClickListener: (Image, ByteArray) -> Unit
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private var imageList = listOf<Image>()
    private var showLoading: Boolean = false
    override fun getItemViewType(position: Int): Int {
        return if (position == imageList.size) {
            LoadingViewHolder.VIEW_TYPE
        } else {
            ImageListViewHolder.VIEW_TYPE
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            ImageListViewHolder.VIEW_TYPE -> ImageListViewHolder(
                LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_image, parent, false),
                faveClickListener
            )

            LoadingViewHolder.VIEW_TYPE -> LoadingViewHolder(
                LayoutInflater.from(parent.context).inflate(
                    R.layout.item_loading,
                    parent, false
                )
            )

            else -> throw IllegalStateException("Not found view type")
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (position == imageList.size || holder !is ImageListViewHolder) return
        val image = imageList[position]
        holder.bindData(image)
    }

    override fun getItemCount(): Int = imageList.size + if (showLoading) 1 else 0

    fun bindProductList(newList: List<Image>, showLoading: Boolean = false) {
        val previousLoading = this.showLoading
        this.showLoading = showLoading
        if (!showLoading && previousLoading) {
            notifyItemRemoved(imageList.size)
        }
        val diffUtil = DiffUtilCallback(imageList, newList)
        val diffResults = DiffUtil.calculateDiff(diffUtil)
        imageList = newList
        diffResults.dispatchUpdatesTo(this)
    }
}

class LoadingViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    companion object {
        const val VIEW_TYPE = 2
    }
}

class ImageListViewHolder(
    itemView: View,
    private val faveClickListener: (Image, ByteArray) -> Unit
) : RecyclerView.ViewHolder(itemView) {

    private val imageView: ImageView = itemView.findViewById(R.id.image)
    private val favoriteIcon: ImageView = itemView.findViewById(R.id.favorite)


    fun bindData(image: Image) {
        favoriteIcon.setColorFilter(
            if (image.isFavorite) {
                Color.RED
            } else {
                Color.WHITE
            }
        )
        favoriteIcon.setOnClickListener {
            val bitmap = imageView.getBitmapArray()
            faveClickListener(image, bitmap)
        }
        imageView.loadImage(image)
    }

    companion object {
        const val VIEW_TYPE = 1
    }
}