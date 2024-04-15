package com.example.minlmedia


import com.example.minlmedia.data.apiCalls.dto.LoginResponse
import com.example.minlmedia.data.apiCalls.dto.RegisterResponse
import io.ktor.client.*
import io.ktor.client.engine.cio.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import io.ktor.client.request.forms.submitForm
import io.ktor.client.plugins.RedirectResponseException
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import io.ktor.client.request.forms.formData
import io.ktor.client.request.forms.submitFormWithBinaryData
import java.io.File

// ----------------------------------- Testing Function -----------------------------------------------------------

suspend fun main() {
    val client = HttpClient(CIO)

    val response: HttpResponse = client.submitFormWithBinaryData(
        url = "https://k8fm9r7b-3456.uks1.devtunnels.ms/upload/upload?authorID=1&author=Mojtaba",
        formData = formData {
            append("name", "portsudna")
            append("image", File("/home/mojtaba/AndroidStudioProjects/MinlMedia/app/src/main/java/com/example/minlmedia/portsudan.jpg").readBytes(), Headers.build {
                append(HttpHeaders.ContentType, "image/png")
                append(HttpHeaders.ContentDisposition, "filename=\"ktor_logo.png\"")
            })
        }
    )
    val jsonString : String = response.bodyAsText()
    println(jsonString)

}





// ----------------------------------- Sign Function -----------------------------------------------------------

suspend fun signCall(name: String,email:String, password:String, passwordConfirm: String): RegisterResponse {

    val client = HttpClient(CIO)
    val baseUrl = "https://k8fm9r7b-3456.uks1.devtunnels.ms/auth/register"
    try {
        val response: HttpResponse = client.submitForm(
            url = baseUrl,
            formParameters = parameters {
                append("email", email)
                append("password", password)
                append("name", name)
                append("passwordConfirm", passwordConfirm)
            } ,
        )
        val jsonString : String = response.bodyAsText()
        val gson = Gson()
        val userResponses: RegisterResponse = gson.fromJson(jsonString, RegisterResponse::class.java)
        return(userResponses)
    } catch (e : RedirectResponseException){
        println("Catch1")
        println(e.response.status.description)
    }catch (e : Exception){
        println("Catch2")
        println(e.message)
    }
    return RegisterResponse("s")
}



// ----------------------------------- Login Function -----------------------------------------------------------



suspend fun logInCall(email:String, password:String): LoginResponse {

    val client = HttpClient(CIO)
    val baseUrl = "https://k8fm9r7b-3456.uks1.devtunnels.ms/auth/login"
    try {
        val response: HttpResponse = client.submitForm(
            url = baseUrl,
            formParameters = parameters {
                append("email", email)
                append("password", password)
            } ,
        )
        val jsonString : String = response.bodyAsText()
        val gson = Gson()
        val userResponse: LoginResponse = gson.fromJson(jsonString, LoginResponse::class.java)
        return(userResponse)
    } catch (e : RedirectResponseException){
        println("Catch1")
        println(e.response.status.description)
    }catch (e : Exception){
        println("Catch2")
        println(e.message)
    }
    return LoginResponse(1,"s","s",2,"s","s")
}



// ----------------------------------- Images Function -----------------------------------------------------------


suspend fun getImages(url : String) : List<Item> {

    val client = HttpClient(CIO)
    try {
        val response: HttpResponse = client.request(url) {
            method = HttpMethod.Get
        }
        val jsonString : String = response.bodyAsText()
        val itemType = object : TypeToken<List<Item>>() {}.type
        val items: List<Item> = Gson().fromJson(jsonString, itemType)
        println(items)
        return(items)
    } catch (e : RedirectResponseException){
        println("Catch1")
        println(e.response.status.description)
    }catch (e : Exception){
        println("Catch2")
        println(e.message)
    }
    return(emptyList())
}


// ----------------------------------- Upload Function -----------------------------------------------------------


suspend fun upload(path: String?) {
    val client = HttpClient(CIO)


    val response: HttpResponse = client.submitFormWithBinaryData(
        url = "https://k8fm9r7b-3456.uks1.devtunnels.ms/upload/upload?authorID=1&author=Mojtaba",
        formData = formData {
            append("name", "portsudna")
            append("image", File(path).readBytes(), Headers.build {
                append(HttpHeaders.ContentType, "image/png")
                append(HttpHeaders.ContentDisposition, "filename=\"ktor_logo.png\"")
            })
        }
    )
    println("whhhhhhhhhhhhhhhhhhhhhhhh")
    val jsonString : String = response.bodyAsText()
    val gson = Gson()
    println(jsonString)

}