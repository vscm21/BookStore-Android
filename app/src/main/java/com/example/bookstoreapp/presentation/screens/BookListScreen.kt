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
import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.itemKey
import com.example.bookstoreapp.R
import com.example.bookstoreapp.domain.model.Book
import com.example.bookstoreapp.presentation.components.BookCard
import com.example.bookstoreapp.presentation.components.ErrorMessage
import com.example.bookstoreapp.presentation.components.LoadingIndicator
import com.example.bookstoreapp.presentation.viewmodel.BooksViewModel
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.MaterialTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BookListScreen(onBookClick: (Book) -> Unit) {

    val booksViewModel: BooksViewModel = hiltViewModel()
    val books = booksViewModel.getBooks("android").collectAsLazyPagingItems()
    val favoriteBooks by booksViewModel.favoriteBooks.collectAsState()
    var showOnlyFavorites by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "📚 BookStore",
                        fontWeight = FontWeight.Bold
                    )
                },
                actions = {
                    IconButton(onClick = { showOnlyFavorites = !showOnlyFavorites }) {
                        BadgedBox(
                            badge = {
                                if (showOnlyFavorites) {
                                    Badge()
                                }
                            }
                        ) {
                            Icon(
                                imageVector = if (showOnlyFavorites)
                                    Icons.Default.Favorite
                                else
                                    Icons.Default.FavoriteBorder,
                                contentDescription = null,
                                tint = if (showOnlyFavorites)
                                    MaterialTheme.colorScheme.primary
                                else
                                    MaterialTheme.colorScheme.onSurface
                            )
                        }
                    }
                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            if (showOnlyFavorites) {
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
                        BookCard(book = book, onBookClick = { onBookClick(book) })
                    }
                }
            } else {
                when (books.loadState.refresh) {
                    is LoadState.Loading -> LoadingIndicator()
                    is LoadState.Error -> {
                        val error = books.loadState.refresh as LoadState.Error
                        ErrorMessage(error.error.message ?: "An error occurred")
                    }
                    is LoadState.NotLoading -> {
                        LazyVerticalGrid(columns = GridCells.Fixed(2)) {
                            items(
                                count = books.itemCount,
                                key = books.itemKey { it.id }
                            ) { bookIndex ->
                                val book = books[bookIndex]
                                book?.let {
                                    BookCard(book = it, onBookClick = { onBookClick(it) })
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}