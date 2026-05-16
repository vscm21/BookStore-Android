package com.example.bookstoreapp.di

import com.example.bookstoreapp.data.local.entity.FavoriteBookEntity
import com.example.bookstoreapp.data.remote.BooksApi
import com.example.bookstoreapp.data.remote.dto.BooksApi
import com.example.bookstoreapp.util.Constants.Companion.BASE_URL
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideRetrofit() : Retrofit {
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY)

        val okhttpClient  = OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .build()

        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(okhttpClient)
            .build()
    }

    @Provides
    @Singleton
    fun provideBooksApi(retrofit : Retrofit) : BooksApi {
        return retrofit.create(BooksApi::class.java)
    }

    @Provides
    @Singleton
    fun provideBooksDatabase() : FavoriteBookEntity
}