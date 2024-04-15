package com.example.minlmedia.data.apiCalls.dto

import kotlinx.serialization.Serializable

@Serializable
data class ResponsesStructure(
    val code : Int,
    val status: String,
    val message: String,
    val id: Int,
    val name: String,
    val avatar: String
)

@Serializable
data class LoginResponse(
    val code: Int,
    val status: String,
    val message: String,
    val id: Int,
    val name: String,
    val avatar: String
)

@Serializable
data class RegisterResponse(
    val message: String,
)




data class ImageResponse(
    val id: Int,
    val file_link: String,
    val author: String,
    val author_id: Int,
    val mediaType: String,
    val likes: Int,
    val liked: String,
    val unliked: String
)