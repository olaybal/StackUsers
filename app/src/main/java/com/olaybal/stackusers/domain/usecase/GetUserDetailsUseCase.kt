package com.olaybal.stackusers.domain.usecase

import com.olaybal.stackusers.domain.model.User
import com.olaybal.stackusers.domain.repository.UserRepository
import io.reactivex.Single
import javax.inject.Inject

class GetUserDetailsUseCase @Inject constructor(
    private val repository: UserRepository
) {

    operator fun invoke(userId: Long): Single<User> {
        return repository.getUserDetails(userId)
    }
}