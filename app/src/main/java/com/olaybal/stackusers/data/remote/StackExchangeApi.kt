package com.olaybal.stackusers.data.remote

import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface StackExchangeApi {

    @GET("users")
    fun searchUsers(
        @Query("inname") name: String,
        @Query("site") site: String = "stackoverflow",
        @Query("pagesize") pageSize: Int = 20,
    ): Single<UsersResponse>

    @GET("users/{ids}")
    fun getUserDetails(
        @retrofit2.http.Path("ids") userId: Long,
        @Query("site") site: String = "stackoverflow",
    ): Single<UsersResponse>
}