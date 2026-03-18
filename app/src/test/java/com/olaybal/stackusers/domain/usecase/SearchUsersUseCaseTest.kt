package com.olaybal.stackusers.domain.usecase

import com.olaybal.stackusers.domain.model.User
import com.olaybal.stackusers.domain.repository.UserRepository
import io.reactivex.Single
import junit.framework.TestCase.assertEquals
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito


class SearchUsersUseCaseTest {

    private lateinit var repository: UserRepository
    private lateinit var useCase: SearchUsersUseCase

    @Before
    fun setup() {
        repository = Mockito.mock(UserRepository::class.java)
        useCase = SearchUsersUseCase(repository)
    }

    @Test
    fun `should sort users alphabetically and limit to 20`() {
        // Given
        val users = (1..30).map {
            User(
                id = it.toLong(),
                name = "User${30 - it}",
                reputation = it,
                avatarUrl = "",
                location = null,
                creationDate = null
            )
        }

        Mockito.`when`(repository.searchUsers("john"))
            .thenReturn(Single.just(users))

        // When
        val result = useCase("john").blockingGet()

        // Then
        assertEquals(20, result.size)

        val sorted = result.sortedBy { it.name }
        assertEquals(sorted, result)
    }
}