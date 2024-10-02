package com.example.imagelistapp.main_list.presentation.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.imagelistapp.R
import com.example.imagelistapp.main_list.domain.entity.Image

class ImageListAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private var productsList = listOf<Image>()
    private var showLoading: Boolean = false
    override fun getItemViewType(position: Int): Int {
        return if (position == productsList.size) {
            LoadingViewHolder.VIEW_TYPE
        } else {
            ImageListViewHolder.VIEW_TYPE
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            ImageListViewHolder.VIEW_TYPE -> ImageListViewHolder(
                LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_image, parent, false)
            )

            LoadingViewHolder.VIEW_TYPE -> LoadingViewHolder(
                LayoutInflater.from(parent.context).inflate(
                    R.layout.item_loading,
                    parent, false
                )
            )

            ErrorViewHolder.VIEW_TYPE -> ErrorViewHolder(
                LayoutInflater.from(parent.context).inflate(
                    R.layout.item_error,
                    parent, false
                )
            )

            else -> throw IllegalStateException("Not found view type")
        }
    }

    override fun getItemCount(): Int = productsList.size + if (showLoading) 1 else 0


    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (position == productsList.size || holder !is ImageListViewHolder) return
        holder.bindData(productsList[position])
    }

    fun bindProductList(newList: List<Image>, showLoading: Boolean = false) {
        val previousLoading = this.showLoading
        this.showLoading = showLoading
        if (!showLoading && previousLoading) {
            notifyItemRemoved(productsList.size)
        }
        val diffUtil = DiffUtilCallback(productsList, newList)
        val diffResults = DiffUtil.calculateDiff(diffUtil)
        productsList = newList
        diffResults.dispatchUpdatesTo(this)
    }
}

class LoadingViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    companion object {
        const val VIEW_TYPE = 2
    }
}

class ErrorViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    companion object {
        const val VIEW_TYPE = 3
    }
}

class ImageListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    private val imageView = itemView.findViewById<ImageView>(R.id.image)
    fun bindData(image: Image) {
        imageView.loadImage(image)
    }

    companion object {
        const val VIEW_TYPE = 1
    }
}