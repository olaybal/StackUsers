package com.olaybal.stackusers.domain.repository

import com.olaybal.stackusers.domain.model.User
import io.reactivex.Single

interface UserRepository {

    fun searchUsers(query: String): Single<List<User>>

    fun getUserDetails(userId: Long): Single<User>
}