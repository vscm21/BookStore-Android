package com.example.bookstoreapp.presentation.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import com.example.bookstoreapp.domain.model.Book
import com.example.bookstoreapp.presentation.components.BookCard
import com.example.bookstoreapp.presentation.components.ErrorMessage
import com.example.bookstoreapp.presentation.components.LoadingIndicator
import com.example.bookstoreapp.presentation.viewmodel.BooksViewModel
import androidx.paging.compose.itemContentType
import androidx.paging.compose.itemKey

@Composable
fun BookListScreen(
    onBookClick: (Book) -> Unit){

    val booksViewModel : BooksViewModel = hiltViewModel()
    val books = booksViewModel.getBooks("android").collectAsLazyPagingItems()


    Surface(
        modifier = Modifier.fillMaxSize()
    ) {
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            when (books.loadState.refresh) {
                is LoadState.Loading -> {
                    LoadingIndicator()
                }

                is LoadState.Error -> {
                    val errorMessage = books.loadState.refresh as LoadState.Error
                    ErrorMessage(errorMessage = errorMessage.error.message?: "An error occurred")
                }

                is LoadState.NotLoading -> {
                    LazyVerticalGrid(
                        columns = GridCells.Fixed(2)
                    ) {
                        items(
                            count = books.itemCount,
                            key =  books.itemKey { it.id }) { bookIndex ->
                            val book = books[bookIndex]
                            book?.let {
                                BookCard(
                                    book = it,
                                    onBookClick = { onBookClick(it)}
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}