package com.olaybal.stackusers.domain.usecase

import com.olaybal.stackusers.domain.model.User
import com.olaybal.stackusers.domain.repository.UserRepository
import io.reactivex.Single

class SearchUsersUseCase(
    private val repository: UserRepository
) {

    operator fun invoke(query: String): Single<List<User>> {
        return repository.searchUsers(query)
            .map { users ->
                users
                    .sortedBy { it.name.lowercase() }
                    .take(MAX_RESULTS)
            }
    }

    private companion object {
        const val MAX_RESULTS = 20
    }
}