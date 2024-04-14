package com.example.minlmedia

import android.graphics.Color
import android.os.Bundle
import android.util.Size
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import com.example.minlmedia.data.remote.dto.LoginResponse
import com.example.minlmedia.databinding.ActivityMainBinding
import kotlin.math.log
import io.ktor.client.*
import io.ktor.client.engine.cio.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import io.ktor.client.call.*
import io.ktor.client.request.forms.submitForm
import com.google.gson.Gson
import io.ktor.util.InternalAPI
import kotlinx.serialization.Serializable




@OptIn(InternalAPI::class)
suspend fun main(){





    val client = HttpClient(CIO)
    val baseUrl = "http://127.0.0.1:3456/auth/login"
    val response: HttpResponse = client.submitForm(
        url = baseUrl,
        formParameters = parameters {
            append("email", "admin@of.com")
            append("password", "yes123")
        }
    )
    val stringBody : String = response.bodyAsText()


    val jsonString = stringBody

    val gson = Gson()
    val userResponse: UserResponse = gson.fromJson(jsonString, UserResponse::class.java)

    println("Code: ${userResponse.code}")
    println("Status: ${userResponse.status}")
    println("Message: ${userResponse.message}")
    println("ID: ${userResponse.id}")
    println("Name: ${userResponse.name}")
    println("Avatar: ${userResponse.avatar}")
    println(userResponse)
}