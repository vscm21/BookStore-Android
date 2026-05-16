package com.example.bookstoreapp.domain.repository

import com.example.bookstoreapp.data.local.entity.FavoriteBookEntity
import kotlinx.coroutines.flow.Flow

interface BooksRepository {

    fun getAllFavoriteBooks(): Flow<List<FavoriteBookEntity>>

    suspend fun insertFavoriteBook(favoriteBookEntity: FavoriteBookEntity)
}