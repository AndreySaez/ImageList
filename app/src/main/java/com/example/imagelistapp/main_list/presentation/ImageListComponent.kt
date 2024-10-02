package com.example.imagelistapp.main_list.presentation

import com.example.imagelistapp.main_list.data.DataModule
import com.example.imagelistapp.main_list.domain.entity.DataMode
import com.example.imagelistapp.main_list.presentation.view.ImageListFragment
import dagger.BindsInstance
import dagger.Component

@Component(
    modules = [
        DataModule::class
    ]
)
interface ImageListComponent {
    fun inject(fragment: ImageListFragment)

    @Component.Factory
    interface Factory {
        fun create(@BindsInstance dataMode: DataMode): ImageListComponent
    }

}