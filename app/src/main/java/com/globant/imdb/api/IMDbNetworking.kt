package com.globant.imdb.api

import com.globant.imdb.data.Constants.BASE_URL
import com.google.gson.GsonBuilder
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import java.util.concurrent.TimeUnit
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object IMDbNetworking {

    private val interceptor = HttpLoggingInterceptor().apply {
        this.level = HttpLoggingInterceptor.Level.BODY
    }

    private val movieClient = OkHttpClient.Builder().apply {
        this.addInterceptor(interceptor)
            .connectTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .retryOnConnectionFailure(true)
    }.build()

    private val retrofitMovies = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .client(movieClient)
        .addConverterFactory(GsonConverterFactory.create(GsonBuilder().create()))
        .addCallAdapterFactory(CoroutineCallAdapterFactory())
        .build()

    val movieData: IMDbApiService = retrofitMovies.create(IMDbApiService::class.java)
}