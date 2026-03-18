package com.olaybal.stackusers.data.repository

import com.olaybal.stackusers.data.local.UserDao
import com.olaybal.stackusers.data.mapper.toDomain
import com.olaybal.stackusers.data.mapper.toEntity
import com.olaybal.stackusers.data.remote.StackExchangeApi
import com.olaybal.stackusers.domain.model.User
import com.olaybal.stackusers.domain.repository.UserRepository
import io.reactivex.Single
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val api: StackExchangeApi,
    private val userDao: UserDao,
) : UserRepository {

    override fun searchUsers(query: String): Single<List<User>> {
        return api.searchUsers(query)
            .map { response ->
                val users = response.items.toDomain()
                userDao.insertUsers(users.toEntity())
                users
            }
            .onErrorResumeNext {
                Single.fromCallable {
                    userDao.searchUsers(query).toDomain()
                }
            }
    }

    override fun getUserDetails(userId: Long): Single<User> {
        return Single.fromCallable {
            userDao.getUserById(userId)?.toDomain()
        }.flatMap { cachedUser ->
            if (cachedUser != null) {
                Single.just(cachedUser)
            } else {
                api.getUserDetails(userId)
                    .map { response ->
                        response.items.firstOrNull()?.toDomain()
                            ?: throw IllegalStateException("User not found")
                    }
                    .doOnSuccess { user ->
                        userDao.insertUser(user.toEntity())
                    }
            }
        }
    }
}