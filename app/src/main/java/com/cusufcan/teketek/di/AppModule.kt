package com.cusufcan.teketek.di

import com.cusufcan.teketek.data.remote.GeminiApi
import com.cusufcan.teketek.data.repository.DebateRepositoryImpl
import com.cusufcan.teketek.data.repository.GeminiRepositoryImpl
import com.cusufcan.teketek.domain.repository.DebateRepository
import com.cusufcan.teketek.domain.repository.GeminiRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    @Singleton
    fun provideDebateRepository(): DebateRepository {
        return DebateRepositoryImpl()
    }

    @Provides
    @Singleton
    fun provideGeminiRepository(api: GeminiApi): GeminiRepository {
        return GeminiRepositoryImpl(api)
    }

    @Provides
    @Singleton
    fun provideRetrofit(): Retrofit =
        Retrofit.Builder().baseUrl("https://teketek-backend.onrender.com")
            .addConverterFactory(GsonConverterFactory.create()).build()

    @Provides
    @Singleton
    fun provideGeminiApi(retrofit: Retrofit): GeminiApi = retrofit.create(GeminiApi::class.java)
}