package com.cusufcan.teketek.di

import com.cusufcan.teketek.data.repository.DebateRepositoryImpl
import com.cusufcan.teketek.domain.repository.DebateRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    @Singleton
    fun provideDebateRepository(): DebateRepository {
        return DebateRepositoryImpl()
    }
}