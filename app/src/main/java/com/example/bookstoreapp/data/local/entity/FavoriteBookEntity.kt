package com.example.bookstoreapp.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(
    tableName = "favorite_books"
)
data class FavoriteBookEntity(
    @PrimaryKey val id: String,
    val title: String,
    val authors: String,
    val description: String?,
    val thumbnail: String?,
    val buyLink: String?
) : Serializable
