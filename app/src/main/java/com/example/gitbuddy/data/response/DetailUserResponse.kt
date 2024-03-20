package com.example.gitbuddy.data.response

import com.google.gson.annotations.SerializedName

data class DetailUserResponse(
    @SerializedName("login")
    val username: String,
    val id: Int,
    @SerializedName("avatar_url")
    val avatarUrl: String,
    @SerializedName("followers_url")
    val followersUrl: String,
    @SerializedName("following_url")
    val followingUrl: String,
    val name: String,
    val followers: String,
    val following: String,
)
