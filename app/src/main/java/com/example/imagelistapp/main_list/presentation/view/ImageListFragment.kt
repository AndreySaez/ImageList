package com.example.imagelistapp.main_list.presentation.view

import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import android.widget.Toolbar
import androidx.core.content.ContextCompat
import androidx.core.os.bundleOf
import androidx.core.view.forEach
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.imagelistapp.R
import com.example.imagelistapp.main_list.domain.entity.DataMode
import com.example.imagelistapp.main_list.presentation.DaggerImageListComponent
import com.example.imagelistapp.main_list.presentation.viewmodel.ImageListActions
import com.example.imagelistapp.main_list.presentation.viewmodel.ImageListOneTimeEvents
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
            .create(getDataMode(), requireContext())
            .inject(this)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val views = Views(view)
        setupToolbar(views)
        val imagesAdapter = ImageListAdapter { image, bitmap ->
            viewModel.onAction(ImageListActions.FavoriteIconClicked(image, bitmap))
        }
        views.errorButton.setOnClickListener {
            viewModel.onAction(ImageListActions.Reload)
        }
        val gridLayoutManager = GridLayoutManager(context, 3).apply {
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
            addItemDecoration(SpacingItemDecorator(4))
            adapter = imagesAdapter
            layoutManager = gridLayoutManager
            addOnScrollListener(object : RecyclerView.OnScrollListener() {
                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    val visibleItemCount = gridLayoutManager.childCount
                    val totalItemCount = gridLayoutManager.itemCount
                    val firstVisibleItemPosition = gridLayoutManager.findFirstVisibleItemPosition()

                    if (visibleItemCount + firstVisibleItemPosition >= totalItemCount && firstVisibleItemPosition >= 0 && !recyclerView.isComputingLayout) {
                        viewModel.onAction(ImageListActions.OnScrolledToEnd)
                    }
                }
            })
        }
        viewModel.event.onEach { event ->
            when (event) {
                is ImageListOneTimeEvents.MakeToast -> Toast.makeText(
                    context,
                    event.stringRes,
                    Toast.LENGTH_LONG
                ).show()
            }

        }.launchIn(lifecycleScope)
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

    private fun setupToolbar(views: Views) {
        val dataMode = getDataMode()
        val toolbar = views.toolbar
        toolbar.title = when (dataMode) {
            DataMode.MAIN -> getString(R.string.main_screen)
            DataMode.FAVORITE -> getString(R.string.favorites_screen)
        }
        toolbar.navigationIcon = if (dataMode == DataMode.FAVORITE) {
            ContextCompat.getDrawable(requireContext(), R.drawable.back_icon)
        } else {
            null
        }
        toolbar.setNavigationOnClickListener {
            activity?.onBackPressedDispatcher?.onBackPressed()
        }
        when (dataMode) {
            DataMode.MAIN -> {
                toolbar.inflateMenu(R.menu.navigation_menu)
                toolbar.menu.forEach {
                    it.setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS)
                }
            }

            DataMode.FAVORITE -> toolbar.menu.clear()
        }
        toolbar.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.favorites_menu -> parentFragmentManager.beginTransaction()
                    .addToBackStack(null)
                    .add(R.id.main, create(DataMode.FAVORITE))
                    .commit()

            }
            return@setOnMenuItemClickListener false
        }


    }

    private fun getDataMode(): DataMode =
        arguments?.getSerializable(DATAMODE_KEY) as? DataMode ?: DataMode.MAIN

    class Views(view: View) {
        val emptyListText: TextView = view.findViewById(R.id.empty_list_text)
        val errorGroup: View = view.findViewById(R.id.error_group)
        val errorButton: Button = view.findViewById(R.id.error_button)
        val recycler: RecyclerView = view.findViewById(R.id.details_recycler_view)
        val progressBar: ProgressBar = view.findViewById(R.id.progressBar)
        val toolbar: Toolbar = view.findViewById(R.id.toolBar)
    }

    companion object {
        fun create(dataMode: DataMode): ImageListFragment = ImageListFragment().apply {
            arguments = bundleOf(DATAMODE_KEY to dataMode)
        }

        const val DATAMODE_KEY = "Data Mode Key"
    }
}