package com.example.bookstoreapp.presentation

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.res.booleanResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.bookstoreapp.presentation.screens.BookDetailScreen
import com.example.bookstoreapp.presentation.screens.BookListScreen
import com.example.bookstoreapp.presentation.viewmodel.BooksViewModel
import dagger.hilt.android.lifecycle.HiltViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BookStoreApp() {
    val navController = rememberNavController()
    val booksViewModel : BooksViewModel = hiltViewModel()

    NavHost(
        navController = navController,
        startDestination = "bookList"
    ) {
        composable("bookList") {
            BookListScreen(
                onBookClick = { book ->
                    booksViewModel.selectBook(book)
                    navController.navigate("bookDetail")
                }
            )
        }
        composable("bookDetail") {
            val book by booksViewModel.selectedBook.collectAsState()
            book?.let {
                BookDetailScreen(bookSelected = it)
            }
        }
    }
}