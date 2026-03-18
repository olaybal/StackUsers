package com.olaybal.stackusers.data.remote

import com.google.gson.annotations.SerializedName

data class UsersResponse(
    @SerializedName("items")
    val items: List<UserDto> = emptyList(),
    @SerializedName("has_more")
    val hasMore: Boolean = false,
)