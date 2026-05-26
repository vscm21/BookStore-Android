package com.example.bookstoreapp.domain.model

import com.example.bookstoreapp.data.local.entity.FavoriteBookEntity

data class Book(
    val id: String,
    val title: String,
    val authors: List<String>,
    val description: String?,
    val thumbnail: String?,
    val buyLink: String?,
    val isFavorite: Boolean = false
)


fun Book.toFavoriteBookEntity(): FavoriteBookEntity {
    return FavoriteBookEntity(
        id = id,
        title = title,
        authors = authors.joinToString(","),
        description = description,
        thumbnail = thumbnail,
        buyLink = buyLink
    )
}