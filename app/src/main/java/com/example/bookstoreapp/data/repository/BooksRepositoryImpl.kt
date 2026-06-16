package com.example.bookstoreapp.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.PagingSource
import com.example.bookstoreapp.data.remote.BooksPagingSource
import com.example.bookstoreapp.data.local.BooksDatabase
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
    override fun getBooksPagingSource(query: String) : PagingSource<Int, Book> {
        return BooksPagingSource(booksApi = booksApi, query = query)
    }


    //----- DB ------/
    override fun getAllFavoriteBooks() = booksDatabase.getFavoriteBookDAO().getAllFavoriteBooks()

    override suspend fun insertFavoriteBookFromDB(favoriteBookEntity: FavoriteBookEntity) =
        booksDatabase.getFavoriteBookDAO().insertFavoriteBook(favoriteBookInsert = favoriteBookEntity)

    override suspend fun deleteFavoriteBookFromDB(favoriteBookDelete: FavoriteBookEntity)  =
        booksDatabase.getFavoriteBookDAO().deleteFavoriteBook(favoriteBookDelete = favoriteBookDelete)
}
