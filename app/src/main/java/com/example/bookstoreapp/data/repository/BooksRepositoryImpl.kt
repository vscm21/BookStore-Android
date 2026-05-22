package com.example.bookstoreapp.data.repository

import androidx.paging.PagingData
import com.example.bookstoreapp.BuildConfig
import com.example.bookstoreapp.data.local.dao.BooksDatabase
import com.example.bookstoreapp.data.local.entity.FavoriteBookEntity
import com.example.bookstoreapp.data.remote.BooksApi
import com.example.bookstoreapp.domain.model.Book
import com.example.bookstoreapp.domain.repository.BooksRepository
import kotlinx.coroutines.flow.Flow

class BooksRepositoryImpl(
    val booksApi: BooksApi,
    val booksDatabase: BooksDatabase
) : BooksRepository {

    //----- api ---- //
    override fun getBooks(query: String) : Flow<PagingData<Book>> {
        TODO("Implement with Paging 3")
    }


    //----- DB ------/
    override fun getAllFavoriteBooks() = booksDatabase.getFavoriteBookDAO().getAllFavoriteBooks()

    override suspend fun insertFavoriteBook(favoriteBookEntity: FavoriteBookEntity) =
        booksDatabase.getFavoriteBookDAO().insertFavoriteBook(favoriteBookInsert = favoriteBookEntity)

    override suspend fun deleteFavoriteBook(favoriteBookDelete: FavoriteBookEntity)  =
        booksDatabase.getFavoriteBookDAO().deleteFavoriteBook(favoriteBookDelete = favoriteBookDelete)
}