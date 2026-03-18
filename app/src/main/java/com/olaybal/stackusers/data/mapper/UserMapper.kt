package com.olaybal.stackusers.data.mapper

import com.olaybal.stackusers.data.local.entity.UserEntity
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

@JvmName("toDomainListFromDto")
fun List<UserDto>.toDomain(): List<User> {
    return map { it.toDomain() }
}

fun UserEntity.toDomain(): User {
    return User(
        id = id,
        name = name,
        reputation = reputation,
        avatarUrl = avatarUrl,
        location = location,
        creationDate = creationDate,
    )
}

@JvmName("toDomainListFromEntity")
fun List<UserEntity>.toDomain(): List<User> {
    return map { it.toDomain() }
}

fun User.toEntity(): UserEntity {
    return UserEntity(
        id = id,
        name = name,
        reputation = reputation,
        avatarUrl = avatarUrl,
        location = location,
        creationDate = creationDate,
    )
}

fun List<User>.toEntity(): List<UserEntity> {
    return map { it.toEntity() }
}
