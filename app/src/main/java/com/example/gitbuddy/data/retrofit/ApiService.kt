package com.example.gitbuddy.data.retrofit

import com.example.gitbuddy.data.response.UserResponse
import com.example.gitbuddy.BuildConfig.*
import com.example.gitbuddy.data.response.DetailUserResponse
import com.example.gitbuddy.data.response.User
import retrofit2.Call
import retrofit2.http.*

interface ApiService {
    @GET("search/users")
    fun getSearchUser(
        @Query("q") query: String
    ): Call<UserResponse>

    @GET("users/{username}")
    fun getDetailUser(
        @Path("username") username: String
    ): Call<DetailUserResponse>

    @GET("users/{username}/followers")
    fun getFollowersUser(
        @Path("username") username: String
    ): Call<ArrayList<User>>

    @GET("users/{username}/following")
    fun getFollowingUser(
        @Path("username") username: String
    ): Call<ArrayList<User>>
}