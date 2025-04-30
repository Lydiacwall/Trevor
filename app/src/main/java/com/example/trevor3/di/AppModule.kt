package com.example.trevor3.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import javax.inject.Named
import javax.inject.Singleton


import okhttp3.logging.HttpLoggingInterceptor


@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    @Singleton
    @Named("BaseRetrofit")
    fun provideRetrofit(): Retrofit {

        val client = OkHttpClient.Builder()
            .build()
        return Retrofit.Builder()
            .baseUrl("http://192.168.1.106:5057/")
            .client(client)
            .addConverterFactory(ScalarsConverterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    private const val API_ID = "76b8bf05"
    private const val API_KEY = "a4cf3f101050c0ac7282592baeb91606"
    private const val BASE_URL_PARSER = "https://api.edamam.com/api/food-database/v2/"
    private const val BASE_URL_AUTOCOMPLETE = "https://api.edamam.com/"

    private fun provideOkHttpClient(): OkHttpClient {
        val logging = HttpLoggingInterceptor()
        logging.setLevel(HttpLoggingInterceptor.Level.BASIC)
        return OkHttpClient.Builder()
            .addInterceptor(logging)
            .addInterceptor { chain ->
                val original = chain.request()
                val originalHttpUrl = original.url

                val url = originalHttpUrl.newBuilder()
                    .addQueryParameter("app_id", API_ID)
                    .addQueryParameter("app_key", API_KEY)
                    .build()

                val requestBuilder = original.newBuilder().url(url)
                val request = requestBuilder.build()
                chain.proceed(request)
            }
            .build()
    }

    @Provides
    @Singleton
    @Named("parserRetrofit")
    fun provideParserRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL_PARSER)
            .client(provideOkHttpClient())
            .addConverterFactory(ScalarsConverterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    @Named("autocompleteRetrofit")
    fun provideAutocompleteRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL_AUTOCOMPLETE)
            .client(provideOkHttpClient())
            .addConverterFactory(ScalarsConverterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
}
