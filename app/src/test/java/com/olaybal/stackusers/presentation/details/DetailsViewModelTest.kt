package com.olaybal.stackusers.presentation.details

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.olaybal.stackusers.domain.model.User
import com.olaybal.stackusers.domain.usecase.GetUserDetailsUseCase
import io.reactivex.Single
import io.reactivex.android.plugins.RxAndroidPlugins
import io.reactivex.plugins.RxJavaPlugins
import io.reactivex.schedulers.Schedulers
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito

class DetailsViewModelTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var getUserDetailsUseCase: GetUserDetailsUseCase
    private lateinit var viewModel: DetailsViewModel

    @Before
    fun setup() {
        RxJavaPlugins.setIoSchedulerHandler { Schedulers.trampoline() }
        RxJavaPlugins.setComputationSchedulerHandler { Schedulers.trampoline() }
        RxJavaPlugins.setNewThreadSchedulerHandler { Schedulers.trampoline() }
        RxAndroidPlugins.setInitMainThreadSchedulerHandler { Schedulers.trampoline() }
        RxAndroidPlugins.setMainThreadSchedulerHandler { Schedulers.trampoline() }

        getUserDetailsUseCase = Mockito.mock(GetUserDetailsUseCase::class.java)
        viewModel = DetailsViewModel(getUserDetailsUseCase)
    }

    @After
    fun tearDown() {
        RxJavaPlugins.reset()
        RxAndroidPlugins.reset()
    }

    @Test
    fun `loadUser should emit success when user details are returned`() {
        val user = User(
            id = 1L,
            name = "John",
            reputation = 100,
            avatarUrl = "https://example.com/avatar.jpg",
            location = "Philippines",
            creationDate = 123456789L,
        )

        Mockito.`when`(getUserDetailsUseCase(1L))
            .thenReturn(Single.just(user))

        viewModel.loadUser(1L)

        assertEquals(DetailsUiState.Success(user), viewModel.uiState.value)
    }

    @Test
    fun `loadUser should emit error when use case fails`() {
        Mockito.`when`(getUserDetailsUseCase(1L))
            .thenReturn(Single.error(Throwable("Something went wrong")))

        viewModel.loadUser(1L)

        assertEquals(
            DetailsUiState.Error("Something went wrong"),
            viewModel.uiState.value,
        )
    }
}