package com.olaybal.stackusers.data.repository

import com.olaybal.stackusers.data.mapper.toDomain
import com.olaybal.stackusers.data.remote.StackExchangeApi
import com.olaybal.stackusers.domain.model.User
import com.olaybal.stackusers.domain.repository.UserRepository
import io.reactivex.Single
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val api: StackExchangeApi
) : UserRepository {

    override fun searchUsers(query: String): Single<List<User>> {
        return api.searchUsers(query)
            .map { response ->
                response.items.toDomain()
            }
    }

    override fun getUserDetails(userId: Long): Single<User> {
        return api.getUserDetails(userId)
            .map { response ->
                response.items.firstOrNull()?.toDomain()
                    ?: throw IllegalStateException("User not found")
            }
    }
}