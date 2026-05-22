package com.example.bookstoreapp.domain.repository

import androidx.paging.PagingData
import com.example.bookstoreapp.data.local.entity.FavoriteBookEntity
import com.example.bookstoreapp.domain.model.Book
import kotlinx.coroutines.flow.Flow

interface BooksRepository {

    // ----- [API] ------ //
    fun getBooks(query: String) : Flow<PagingData<Book>>


    // ---- [DB] ------- //
    fun getAllFavoriteBooks(): Flow<List<FavoriteBookEntity>>

    suspend fun insertFavoriteBook(favoriteBookEntity: FavoriteBookEntity)

    suspend fun deleteFavoriteBook(favoriteBookDelete: FavoriteBookEntity)
}