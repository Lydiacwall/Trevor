package com.example.trevor3.di

import android.content.Context
import com.example.trevor3.data.local.DatabaseHelper
import com.example.trevor3.data.network.ApiHelper
import com.example.trevor3.data.network.ApiService
import com.example.trevor3.data.repository.DatabaseRepositoryImpl
import com.example.trevor3.data.repository.UserRepositoryImpl
import com.example.trevor3.domain.repository.IDatabaseRepository
import com.example.trevor3.domain.repository.IUserRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {
    @Provides
    @Singleton
    fun provideDatabaseHelper(@ApplicationContext context: Context): DatabaseHelper =
        DatabaseHelper(context)

    @Provides
    @Singleton
    fun provideDatabaseRepository(databaseHelper: DatabaseHelper): IDatabaseRepository =
        DatabaseRepositoryImpl(databaseHelper)
}