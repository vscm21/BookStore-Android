package com.example.bookstoreapp.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import com.example.bookstoreapp.data.local.entity.FavoriteBookEntity
import com.example.bookstoreapp.domain.model.Book
import com.example.bookstoreapp.domain.repository.BooksRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BooksViewModel @Inject constructor(
    private val booksRepository: BooksRepository
) : ViewModel() {

    //-- [API with Paging 3] ---
    fun getBooks(query: String) = Pager(
        config = PagingConfig(
            pageSize = 20,
            enablePlaceholders = false
        ),
        pagingSourceFactory = { booksRepository.getBooksPagingSource(query = query) })
        .flow.cachedIn(viewModelScope)

    //---- [DB operations]
    fun insertFavoriteBookFromDB(book: FavoriteBookEntity) = viewModelScope.launch {
        booksRepository.insertFavoriteBookFromDB(book)
    }

    fun deleteFavoriteBookFromDB(bookToDelete: FavoriteBookEntity) = viewModelScope.launch {
        booksRepository.deleteFavoriteBookFromDB(bookToDelete)
    }

    //Continues stream, so it is a value that i will observe it
    val favoriteBooks = booksRepository.getAllFavoriteBooks().stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = emptyList()
    )


    //For passing the book to the detail screen
    private val _selectedBook = MutableStateFlow<Book?>(null)
    val selectedBook: StateFlow<Book?> = _selectedBook

    fun selectBook(book: Book) {
        _selectedBook.value = book
    }

    //If the books is favorite
    fun isBookFavorite(bookId: String) : Flow<Boolean> {
        return favoriteBooks.map { bookList ->
            bookList.any{ it.id == bookId}
        }
    }
}
