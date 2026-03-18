package com.olaybal.stackusers.data.mapper

import com.olaybal.stackusers.data.remote.UserDto
import com.olaybal.stackusers.domain.model.User

fun UserDto.toDomain(): User {
    return User(
        id = userId ?: accountId ?: -1,
        name = displayName.orEmpty(),
        reputation = reputation ?: 0,
        avatarUrl = profileImage.orEmpty(),
        location = location,
        creationDate = creationDate
    )
}

fun List<UserDto>.toDomain(): List<User> {
    return map { it.toDomain() }
}