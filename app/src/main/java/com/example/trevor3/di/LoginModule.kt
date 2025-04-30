package com.example.trevor3.di

import com.example.trevor3.data.network.ApiHelper
import com.example.trevor3.data.network.ApiService
import com.example.trevor3.data.repository.UserRepositoryImpl
import com.example.trevor3.domain.repository.IUserRepository
import com.example.trevor3.domain.usecases.AddUser
import com.example.trevor3.domain.usecases.AuthenticateUser
import com.example.trevor3.domain.usecases.AuthenticationUseCases
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object LoginModule {

    @Provides
    @Singleton
    fun provideApiService(@Named("BaseRetrofit") retrofit: Retrofit): ApiService {
        return retrofit.create(ApiService::class.java)
    }
    @Provides
    @Singleton
    fun provideUserApiHelper(userApiService: ApiService): ApiHelper {
        return ApiHelper(userApiService);
    }

    @Provides
    @Singleton
    fun provideAuthenticationUseCases(repository: IUserRepository): AuthenticationUseCases {
        return AuthenticationUseCases(
            addUser = AddUser(repository),
            authenticateUser = AuthenticateUser(repository)
        )
    }
    @Provides
    @Singleton
    fun provideUserRepository(userApi: ApiHelper): IUserRepository {
        return UserRepositoryImpl(userApi)
    }

}

