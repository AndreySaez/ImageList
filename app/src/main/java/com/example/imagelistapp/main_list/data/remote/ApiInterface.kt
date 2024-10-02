package com.example.imagelistapp.main_list.data.remote

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query


interface ApiInterface {

    @GET("list")
    suspend fun getImages(
        @Query("limit") limit: String = "20",
        @Query("page") page: String = "1",
    ): List<ImageResponse>

    companion object {
        private const val BASE_URL = "https://picsum.photos/v2/"
        private val logging = HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
        fun create(): ApiInterface {
            val client = OkHttpClient.Builder()
                .addInterceptor(logging)
                .build()
            val retrofit = Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .baseUrl(BASE_URL)
                .build()
            return retrofit
                .create(ApiInterface::class.java)

        }
    }
}