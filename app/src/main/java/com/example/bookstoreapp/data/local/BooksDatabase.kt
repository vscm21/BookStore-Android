package com.example.bookstoreapp.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.bookstoreapp.data.local.dao.FavoriteBookDao
import com.example.bookstoreapp.data.local.entity.FavoriteBookEntity

@Database(
    entities = [FavoriteBookEntity::class],
    version = 1
)
abstract class BooksDatabase : RoomDatabase() {
    abstract fun getFavoriteBookDAO() : FavoriteBookDao
}