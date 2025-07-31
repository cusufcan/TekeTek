package com.cusufcan.teketek.di

import com.cusufcan.teketek.BuildConfig
import com.cusufcan.teketek.data.remote.GeminiApi
import com.cusufcan.teketek.data.remote.TopicService
import com.cusufcan.teketek.data.repository.DebateRepositoryImpl
import com.cusufcan.teketek.data.repository.GeminiRepositoryImpl
import com.cusufcan.teketek.domain.repository.DebateRepository
import com.cusufcan.teketek.domain.repository.GeminiRepository
import com.google.firebase.firestore.FirebaseFirestore
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
    fun provideFirestore(): FirebaseFirestore = FirebaseFirestore.getInstance()

    @Provides
    @Singleton
    fun provideTopicService(firestore: FirebaseFirestore): TopicService {
        return TopicService(firestore)
    }

    @Provides
    @Singleton
    fun provideDebateRepository(topicService: TopicService): DebateRepository {
        return DebateRepositoryImpl(topicService)
    }

    @Provides
    @Singleton
    fun provideGeminiRepository(api: GeminiApi): GeminiRepository {
        return GeminiRepositoryImpl(api)
    }

    @Provides
    @Singleton
    fun provideRetrofit(): Retrofit =
        Retrofit.Builder().baseUrl(BuildConfig.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create()).build()

    @Provides
    @Singleton
    fun provideGeminiApi(retrofit: Retrofit): GeminiApi = retrofit.create(GeminiApi::class.java)
}