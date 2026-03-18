package com.olaybal.stackusers.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "users")
data class UserEntity(
    @PrimaryKey
    val id: Long,
    val name: String,
    val reputation: Int,
    val avatarUrl: String,
    val location: String?,
    val creationDate: Long?,
)