package com.olaybal.stackusers.domain.usecase

import com.olaybal.stackusers.domain.model.User
import com.olaybal.stackusers.domain.repository.UserRepository
import io.reactivex.Single

class SearchUsersUseCase(
    private val repository: UserRepository
) {

    operator fun invoke(query: String): Single<List<User>> {
        return repository.searchUsers(query)
    }
}