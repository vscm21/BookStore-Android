package com.example.bookstoreapp.data.remote

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.bookstoreapp.data.remote.dto.BookItem
import com.example.bookstoreapp.domain.model.Book
import retrofit2.HttpException
import kotlin.collections.emptyList

class BooksPagingSource(
    private val booksApi: BooksApi,
    private val query: String
) : PagingSource<Int, Book>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Book> {
        val pageNumber = params.key ?: 0

        return try {
            val response = booksApi.getBooks(
                query = query,
                maxResults = params.loadSize,
                startIndex = pageNumber * params.loadSize
            )

            val books = response.items?.map { it.toBook() } ?: emptyList()

            LoadResult.Page(
                data = books,
                prevKey = if (pageNumber == 0) null else pageNumber - 1,
                nextKey = if (books.isEmpty()) null else pageNumber + 1
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        } catch (e: HttpException){ // for non 2-xx HTTP STATUS code.
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, Book>): Int? = null
}


private fun BookItem.toBook(): Book {
    return Book(
        id = id,
        title = volumeInfo.title ?: "Unknown Title",
        authors = volumeInfo.authors ?: emptyList(),
        description = volumeInfo.description,
        thumbnail = volumeInfo.imageLinks?.thumbnail,
        buyLink = volumeInfo.saleInfo?.buyLink,
        isFavorite = false
    )
}