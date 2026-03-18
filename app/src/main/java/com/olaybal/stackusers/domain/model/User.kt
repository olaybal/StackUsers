package com.olaybal.stackusers.domain.model

data class User(
    val id: Long,
    val name: String,
    val reputation: Int,
    val avatarUrl: String,
    val location: String?,
    val creationDate: Long?
)