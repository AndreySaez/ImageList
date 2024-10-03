package com.example.imagelistapp.main_list.data.remote

import dagger.Module
import dagger.Provides
import retrofit2.Retrofit

@Module
class RetrofitModule {
    @Provides
    fun retrofit() = ApiInterface.createRetrofit()

    @Provides
    fun apiInterface(retrofit: Retrofit): ApiInterface {
        return retrofit.create(ApiInterface::class.java)
    }
}