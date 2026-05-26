package com.example.bookstoreapp.presentation.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
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
import com.example.bookstoreapp.R

@Composable
fun BookListScreen(
    onBookClick: (Book) -> Unit){

    val booksViewModel : BooksViewModel = hiltViewModel()
    val books = booksViewModel.getBooks("android").collectAsLazyPagingItems()
    val favoriteBooks by booksViewModel.favoriteBooks.collectAsState()
    var showOnlyFavorites by remember { mutableStateOf(false) }


    Surface(modifier = Modifier.fillMaxSize()) {
        Column(modifier = Modifier.fillMaxSize()) {

            FilterChip(
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
                selected = showOnlyFavorites,
                onClick = { showOnlyFavorites = !showOnlyFavorites },
                label = { Text(stringResource(R.string.favorites)) },
                leadingIcon = {
                    Icon(
                        imageVector = if (showOnlyFavorites) Icons.Default.Favorite else
                            Icons.Default.FavoriteBorder,
                        contentDescription = null
                    )
                }
            )

            if (showOnlyFavorites) {
                // Show favorites from DB
                LazyVerticalGrid(columns = GridCells.Fixed(2)) {
                    items(items = favoriteBooks, key = { it.id }) { entity ->
                        val book = Book(
                            id = entity.id,
                            title = entity.title,
                            authors = entity.authors.split(","),
                            description = entity.description,
                            thumbnail = entity.thumbnail,
                            buyLink = entity.buyLink
                        )
                        BookCard(
                            book = book,
                            onBookClick = { onBookClick(book) }
                        )
                    }
                }
            }else {
                when (books.loadState.refresh) {
                    is LoadState.Loading -> {
                        LoadingIndicator()
                    }

                    is LoadState.Error -> {
                        val errorMessage = books.loadState.refresh as LoadState.Error
                        ErrorMessage(
                            errorMessage = errorMessage.error.message ?: "An error occurred"
                        )
                    }

                    is LoadState.NotLoading -> {
                        LazyVerticalGrid(
                            columns = GridCells.Fixed(2)
                        ) {
                            items(
                                count = books.itemCount,
                                key = books.itemKey { it.id }) { bookIndex ->
                                val book = books[bookIndex]
                                book?.let {
                                    BookCard(
                                        book = it,
                                        onBookClick = { onBookClick(it) }
                                    )
                                }
                            }
                        }
                    }


                }
            }
        }
    }
}