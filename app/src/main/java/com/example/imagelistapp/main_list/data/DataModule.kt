package com.example.imagelistapp.main_list.data

import com.example.imagelistapp.main_list.data.local.ImageListDao
import com.example.imagelistapp.main_list.data.local.LocalDataSource
import com.example.imagelistapp.main_list.data.local.RoomModule
import com.example.imagelistapp.main_list.data.remote.RemoteDataSource
import com.example.imagelistapp.main_list.data.remote.RetrofitModule
import com.example.imagelistapp.main_list.domain.Repository
import com.example.imagelistapp.main_list.domain.entity.DataMode
import dagger.Binds
import dagger.Module
import dagger.Provides
import javax.inject.Provider
import javax.inject.Qualifier

@Module(
    includes = [
        DataModule.Declarations::class,
        RetrofitModule::class,
        RoomModule::class
    ]
)
class DataModule {
    @Provides
    fun repository(
        imageListDao: ImageListDao,
        dataMode: DataMode,
        @Declarations.Remote remote: Provider<DataSource>,
        @Declarations.Local local: Provider<DataSource>
    ): Repository {
        val dataSource =
            when (dataMode) {
                DataMode.MAIN -> remote.get()
                DataMode.FAVORITE -> local.get()
            }
        return RepositoryImpl(dataSource, imageListDao)
    }

    @Module
    interface Declarations {

        @Remote
        @Binds
        fun remote(impl: RemoteDataSource): DataSource

        @Local
        @Binds
        fun local(impl: LocalDataSource): DataSource

        @Qualifier
        annotation class Remote

        @Qualifier
        annotation class Local

    }
}