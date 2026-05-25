package com.example.bookstoreapp.presentation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

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