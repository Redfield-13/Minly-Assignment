package com.example.minlmedia.data.remote.dto

import kotlinx.serialization.Serializable

@Serializable
data class LoginResponse(
    val code : Int,
    val status: String,
    val message: String,
    val id: Int,
    val name: String,
    val avatar: String
)