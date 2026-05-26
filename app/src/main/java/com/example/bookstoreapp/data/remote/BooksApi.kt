package com.example.bookstoreapp.data.remote

import com.example.bookstoreapp.BuildConfig
import com.example.bookstoreapp.data.remote.dto.BookResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface BooksApi {

    @GET("volumes")
    suspend fun getBooks(
        @Query("q") query: String,
        @Query("maxResults") maxResults : Int = 20,
        @Query("startIndex") startIndex: Int = 0,
        @Query("key") apiKey: String = BuildConfig.BOOK_API_KEY,
        ) : BookResponse
}