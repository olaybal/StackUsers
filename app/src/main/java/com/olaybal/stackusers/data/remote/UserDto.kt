package com.olaybal.stackusers.data.remote

import com.google.gson.annotations.SerializedName

data class UserDto(
    @SerializedName("account_id")
    val accountId: Long?,
    @SerializedName("user_id")
    val userId: Long?,
    @SerializedName("display_name")
    val displayName: String?,
    @SerializedName("profile_image")
    val profileImage: String?,
    @SerializedName("reputation")
    val reputation: Int?,
    @SerializedName("location")
    val location: String?,
    @SerializedName("creation_date")
    val creationDate: Long?,
)