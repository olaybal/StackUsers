package com.olaybal.stackusers.domain.usecase

import com.olaybal.stackusers.domain.model.User
import com.olaybal.stackusers.domain.repository.UserRepository
import io.reactivex.Single
import junit.framework.TestCase.assertEquals
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito


class GetUserDetailsUseCaseTest {

    private lateinit var repository: UserRepository
    private lateinit var useCase: GetUserDetailsUseCase

    @Before
    fun setup() {
        repository = Mockito.mock(UserRepository::class.java)
        useCase = GetUserDetailsUseCase(repository)
    }

    @Test
    fun `should return user details`() {
        // Given
        val user = User(
            id = 1,
            name = "John",
            reputation = 1000,
            avatarUrl = "",
            location = "PH",
            creationDate = 123456
        )

        Mockito.`when`(repository.getUserDetails(1))
            .thenReturn(Single.just(user))

        // When
        val result = useCase(1).blockingGet()

        // Then
        assertEquals(user, result)
    }
}