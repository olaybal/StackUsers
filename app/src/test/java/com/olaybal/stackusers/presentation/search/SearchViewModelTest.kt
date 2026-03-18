package com.olaybal.stackusers.presentation.search

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.olaybal.stackusers.domain.model.User
import com.olaybal.stackusers.domain.usecase.SearchUsersUseCase
import io.reactivex.Single
import io.reactivex.android.plugins.RxAndroidPlugins
import io.reactivex.plugins.RxJavaPlugins
import io.reactivex.schedulers.Schedulers
import junit.framework.TestCase.assertEquals
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito


class SearchViewModelTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var searchUsersUseCase: SearchUsersUseCase
    private lateinit var viewModel: SearchViewModel

    @Before
    fun setup() {
        RxJavaPlugins.setIoSchedulerHandler { Schedulers.trampoline() }
        RxJavaPlugins.setComputationSchedulerHandler { Schedulers.trampoline() }
        RxJavaPlugins.setNewThreadSchedulerHandler { Schedulers.trampoline() }
        RxAndroidPlugins.setInitMainThreadSchedulerHandler { Schedulers.trampoline() }

        searchUsersUseCase = Mockito.mock(SearchUsersUseCase::class.java)
        viewModel = SearchViewModel(searchUsersUseCase)
    }

    @After
    fun tearDown() {
        RxJavaPlugins.reset()
        RxAndroidPlugins.reset()
    }

    @Test
    fun `search should emit success when users are returned`() {
        val users = listOf(
            User(
                id = 1L,
                name = "John",
                reputation = 100,
                avatarUrl = "",
                location = "PH",
                creationDate = 1234L,
            ),
        )

        Mockito.`when`(searchUsersUseCase("john"))
            .thenReturn(Single.just(users))

        viewModel.search("john")

        assertEquals(SearchUiState.Success(users), viewModel.uiState.value)
    }

    @Test
    fun `search should emit empty when no users are returned`() {
        Mockito.`when`(searchUsersUseCase("john"))
            .thenReturn(Single.just(emptyList()))

        viewModel.search("john")

        assertEquals(SearchUiState.Empty, viewModel.uiState.value)
    }

    @Test
    fun `search should emit error when query is blank`() {
        viewModel.search("")

        assertEquals(
            SearchUiState.Error("Please enter a username"),
            viewModel.uiState.value,
        )
    }

    @Test
    fun `search should emit error when use case fails`() {
        Mockito.`when`(searchUsersUseCase("john"))
            .thenReturn(Single.error(Throwable("Something went wrong")))

        viewModel.search("john")

        assertEquals(
            SearchUiState.Error("Something went wrong"),
            viewModel.uiState.value,
        )
    }
}