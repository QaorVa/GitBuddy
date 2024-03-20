package com.example.gitbuddy.data.response

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class User(
    @SerializedName("login")
    val username: String,
    val id: Int,
    @SerializedName("avatar_url")
    val avatarUrl: String
) : Parcelable
