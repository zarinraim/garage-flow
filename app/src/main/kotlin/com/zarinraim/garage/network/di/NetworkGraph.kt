package com.zarinraim.garage.network.di

import com.zarinraim.garage.network.data.GarageApiService
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import org.koin.core.module.dsl.new
import org.koin.dsl.module
import retrofit2.Converter
import retrofit2.Retrofit
import retrofit2.converter.kotlinx.serialization.asConverterFactory
import java.util.concurrent.TimeUnit

object NetworkGraph {

    val module = module {
        single { new(::provideHttpClient) }
        single { new(::provideRetrofit) }
        single { new(::provideService) }
    }

    private fun provideHttpClient() = OkHttpClient.Builder()
        .readTimeout(60, TimeUnit.SECONDS)
        .connectTimeout(60, TimeUnit.SECONDS)
        .build()

    @OptIn(ExperimentalSerializationApi::class)
    private val networkJson = Json {
        ignoreUnknownKeys = true
        explicitNulls = false
    }

    private fun converter(): Converter.Factory {
        return networkJson.asConverterFactory("application/json; charset=UTF8".toMediaType())
    }

    private fun provideRetrofit(okHttpClient: OkHttpClient) = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .client(okHttpClient)
        .addConverterFactory(converter())
        .build()

    private fun provideService(retrofit: Retrofit): GarageApiService {
        return retrofit.create(GarageApiService::class.java)
    }

    private const val BASE_URL = "https://beegreen-hackathon.web.app/"
}