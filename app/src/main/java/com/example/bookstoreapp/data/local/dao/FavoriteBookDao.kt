package com.example.bookstoreapp.data.local.dao

import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.bookstoreapp.data.local.entity.FavoriteBookEntity
import kotlinx.coroutines.flow.Flow

interface FavoriteBookDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFavoriteBook(favoriteBookInsert: FavoriteBookEntity) : Long

    @Delete
    suspend fun deleteFavoriteBook(favoriteBookDelete: FavoriteBookEntity)

    @Query("SELECT * FROM favorite_books")
    fun getAllFavoriteBooks() : Flow<List<FavoriteBookEntity>>
}