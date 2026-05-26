package com.example.bookstoreapp.data.local.dao

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.bookstoreapp.data.local.entity.FavoriteBookEntity

@Database(
    entities = [FavoriteBookEntity::class],
    version = 2
)
abstract class BooksDatabase : RoomDatabase() {

    abstract fun getFavoriteBookDAO() : FavoriteBookDao

    companion object {

        @Volatile
        private var instanceDB : BooksDatabase? = null

        private var LOCK = Any()

        operator fun invoke(context: Context) = instanceDB ?: synchronized(LOCK) {
            instanceDB ?: createDatabase(context).also { instanceDB = it }
        }

        private fun createDatabase(context: Context) =
            Room.databaseBuilder(
                context = context.applicationContext,
                klass = BooksDatabase::class.java,
                name = "favorite_books"
            )
                .fallbackToDestructiveMigration()
                .build()

    }
}