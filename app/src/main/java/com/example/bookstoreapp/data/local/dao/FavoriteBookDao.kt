package com.example.bookstoreapp.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.bookstoreapp.data.local.entity.FavoriteBookEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface FavoriteBookDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFavoriteBook(favoriteBookInsert: FavoriteBookEntity)

    @Delete
    suspend fun deleteFavoriteBook(favoriteBookDelete: FavoriteBookEntity)

    @Query("SELECT * FROM favorite_books")
    fun getAllFavoriteBooks() : Flow<List<FavoriteBookEntity>>
}