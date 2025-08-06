package com.cusufcan.teketek.di

import com.cusufcan.teketek.BuildConfig
import com.cusufcan.teketek.data.remote.DebateRemoteDataSource
import com.cusufcan.teketek.data.remote.DebateService
import com.cusufcan.teketek.data.remote.TopicService
import com.cusufcan.teketek.data.repository.DebateRepositoryImpl
import com.cusufcan.teketek.data.repository.TopicRepositoryImpl
import com.cusufcan.teketek.domain.repository.DebateRepository
import com.cusufcan.teketek.domain.repository.TopicRepository
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
    fun provideRetrofit(): Retrofit = Retrofit.Builder().baseUrl(BuildConfig.BASE_URL)
        .addConverterFactory(GsonConverterFactory.create()).build()

    @Provides
    @Singleton
    fun provideTopicService(firestore: FirebaseFirestore): TopicService {
        return TopicService(firestore)
    }

    @Provides
    @Singleton
    fun provideDebateService(retrofit: Retrofit): DebateService =
        retrofit.create(DebateService::class.java)

    @Provides
    @Singleton
    fun provideTopicRepository(topicService: TopicService): TopicRepository {
        return TopicRepositoryImpl(topicService)
    }

    @Provides
    @Singleton
    fun provideDebateRepository(debateRemoteDataSource: DebateRemoteDataSource): DebateRepository {
        return DebateRepositoryImpl(debateRemoteDataSource)
    }
}