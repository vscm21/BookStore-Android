package com.example.bookstoreapp.domain.model

data class Book(
    val id: String,
    val title: String,
    val authors: List<String>,
    val description: String?,
    val thumbnail: String?,
    val buyLink: String?,
    val isFavorite: Boolean = false
)
