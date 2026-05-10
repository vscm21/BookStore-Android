package com.example.bookstoreapp.data.remote.dto

data class BookResponse(
    val items: List<BookItem>?,
    val totalItems: Int
)

data class BookItem(
    val id: String,
    val volumeInfo: VolumeInfo
)

data class VolumeInfo(
    val title: String?,
    val authors: List<String>?,
    val description: String?,
    val imageLinks: ImageLinks?,
    val saleInfo: SaleInfo?
)

data class ImageLinks(
    val thumbnail: String?
)

data class SaleInfo(
    val buyLink: String?
)
