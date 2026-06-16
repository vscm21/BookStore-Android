package com.example.bookstoreapp.presentation.viewmodel

import app.cash.turbine.test
import com.example.bookstoreapp.data.local.entity.FavoriteBookEntity
import com.example.bookstoreapp.domain.repository.BooksRepository
import io.mockk.coVerify
import io.mockk.mockk
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Test

class BooksViewModelTest {

    private lateinit var booksRepository: BooksRepository
    private lateinit var booksViewModel: BooksViewModel
    private val testDispatcher = StandardTestDispatcher()

    @Before
    fun setup(){
        Dispatchers.setMain(testDispatcher)
        booksRepository = mockk(relaxed = true)
        booksViewModel = BooksViewModel(booksRepository = booksRepository)
    }

    @After
    fun tearDown(){
        Dispatchers.resetMain()
    }


    @Test
    fun `insertFavoriteBook calls repository insertFavoriteBook`() = runTest {
        //Given
        val book = FavoriteBookEntity(
            id = "1",
            title = "Maiden",
            authors = "Freida",
            description = "Test Description",
            thumbnail = null,
            buyLink = null
        )
        //When
        booksViewModel.insertFavoriteBookFromDB(book)
        testDispatcher.scheduler.advanceUntilIdle()

        //Then
        coVerify { booksRepository.insertFavoriteBookFromDB(book) }
    }

    @Test
    fun `deleteFavoriteBook calls repository deleteFavoriteBook`() = runTest {
        //Given
        val bookToDelete = FavoriteBookEntity(
            id = "1",
            title = "Maiden",
            authors = "Freida",
            description = "Test Description",
            thumbnail = null,
            buyLink = null
        )
        //When
        booksViewModel.deleteFavoriteBookFromDB(bookToDelete)
        testDispatcher.scheduler.advanceUntilIdle()

        //Then
        coVerify { booksRepository.deleteFavoriteBookFromDB(bookToDelete) }
    }

    @Test
    fun `isFavorite returns true when book is in Favorites`() = runTest {
        // Given
        val bookId = "1"
        val favoritesBooks = listOf(
            FavoriteBookEntity(
                id = bookId,
                title = "Maiden",
                authors = "Freida",
                description = null,
                thumbnail = null,
                buyLink = null
            )
        )

        io.mockk.every { booksRepository.getAllFavoriteBooks() } returns flowOf(favoritesBooks)

        // When + Then
        booksViewModel.isBookFavorite(bookId).test {
            assertTrue(awaitItem())
            cancelAndIgnoreRemainingEvents()
        }
    }
}