package com.example.imagelistapp.main_list.presentation.view

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.imagelistapp.R
import com.example.imagelistapp.main_list.domain.entity.DataMode
import com.example.imagelistapp.main_list.presentation.DaggerImageListComponent
import com.example.imagelistapp.main_list.presentation.viewmodel.Action
import com.example.imagelistapp.main_list.presentation.viewmodel.ImageListState
import com.example.imagelistapp.main_list.presentation.viewmodel.ImageListViewModel
import com.example.imagelistapp.main_list.presentation.viewmodel.ImageListViewModelFactory
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject
import javax.inject.Provider

class ImageListFragment : Fragment(R.layout.fragment_image_list) {
    private val viewModel by viewModels<ImageListViewModel> { viewmodelFactory.get() }

    @Inject
    lateinit var viewmodelFactory: Provider<ImageListViewModelFactory>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        DaggerImageListComponent.factory()
            .create(DataMode.MAIN)
            .inject(this)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val views = Views(view)
        val imagesAdapter = ImageListAdapter()
        views.errorButton.setOnClickListener {
            viewModel.onAction(Action.Reload)
        }
        val gridLayoutManager = GridLayoutManager(context, 2).apply {
            spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
                override fun getSpanSize(position: Int): Int {
                    return when (imagesAdapter.getItemViewType(position)) {
                        LoadingViewHolder.VIEW_TYPE -> 2
                        else -> 1
                    }
                }
            }
        }
        views.recycler.apply {
            adapter = imagesAdapter
            layoutManager = gridLayoutManager
            addOnScrollListener(object : RecyclerView.OnScrollListener() {
                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    val visibleItemCount = gridLayoutManager.childCount
                    val totalItemCount = gridLayoutManager.itemCount
                    val firstVisibleItemPosition = gridLayoutManager.findFirstVisibleItemPosition()

                    if (visibleItemCount + firstVisibleItemPosition >= totalItemCount && firstVisibleItemPosition >= 0 && !recyclerView.isComputingLayout) {
                        viewModel.onAction(Action.OnScrolledToEnd)
                    }
                }
            })
        }
        viewModel.state.onEach { state ->
            when (state) {
                is ImageListState.ImageList -> {
                    views.emptyListText.isVisible = false
                    views.errorGroup.isVisible = false
                    views.recycler.isVisible = true
                    views.progressBar.isVisible = false
                    imagesAdapter.bindProductList(
                        newList = state.list,
                        showLoading = state.hasMoreItems
                    )
                }

                ImageListState.IsEmpty -> {
                    views.emptyListText.isVisible = true
                    views.errorGroup.isVisible = false
                    views.recycler.isVisible = false
                    views.progressBar.isVisible = false
                }

                ImageListState.IsError -> {
                    views.emptyListText.isVisible = false
                    views.errorGroup.isVisible = true
                    views.progressBar.isVisible = false
                    views.recycler.isVisible = false
                }

                ImageListState.IsLoading -> {
                    views.emptyListText.isVisible = false
                    views.errorGroup.isVisible = false
                    views.recycler.isVisible = false
                    views.progressBar.isVisible = true
                    gridLayoutManager.scrollToPosition(0)
                }
            }

        }.launchIn(lifecycleScope)
    }

    class Views(view: View) {
        val emptyListText: TextView = view.findViewById(R.id.empty_list_text)
        val errorGroup: View = view.findViewById(R.id.error_group)
        val errorButton: Button = view.findViewById(R.id.error_button)
        val recycler: RecyclerView = view.findViewById(R.id.details_recycler_view)
        val progressBar: ProgressBar = view.findViewById(R.id.progressBar)
    }
}