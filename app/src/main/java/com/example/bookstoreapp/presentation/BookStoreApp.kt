package com.example.bookstoreapp.presentation

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.bookstoreapp.presentation.screens.BookDetailScreen
import com.example.bookstoreapp.presentation.screens.BookListScreen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BookStoreApp() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = "bookList"
    ) {
        composable("bookList") {
            BookListScreen(
                onBookClick = { book ->
                    navController.navigate("bookDetail")
                }
            )
        }
        composable("bookDetail") {
            BookDetailScreen()
        }
    }
}