package com.example.imagelistapp.main_list.data

import com.example.imagelistapp.main_list.data.remote.ApiInterface
import com.example.imagelistapp.main_list.data.remote.RemoteDataSource
import com.example.imagelistapp.main_list.domain.Repository
import dagger.Binds
import dagger.Module
import dagger.Provides
import javax.inject.Provider
import javax.inject.Qualifier

@Module(includes = [DataModule.Declarations::class])
class DataModule {
    @Provides
    fun repository(
        @Declarations.Remote
        remote: Provider<DataSource>
    ): Repository {
        val dataSource = remote.get()
        return RepositoryImpl(dataSource)
    }

    @Provides
    fun apiInterface() = ApiInterface.create()

    @Module
    interface Declarations {

        @Remote
        @Binds
        fun remote(impl: RemoteDataSource): DataSource

        @Qualifier
        annotation class Remote

    }
}