package com.example.minlmedia

import android.annotation.SuppressLint
import android.content.Intent
import android.os.AsyncTask
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import io.ktor.client.*
import io.ktor.client.engine.cio.*
import io.ktor.client.statement.*
import io.ktor.http.*
import io.ktor.client.request.forms.submitForm
import androidx.lifecycle.ViewModel
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.viewModelScope
import com.google.gson.Gson
import io.ktor.client.call.body
import io.ktor.client.plugins.RedirectResponseException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json

@Serializable
data class UserResponse(
    val code: Int,
    val status: String,
    val message: String,
    val id: Int,
    val name: String,
    val avatar: String
)
suspend fun logInCall(email:String, password:String): UserResponse {

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
        val userResponse: UserResponse = gson.fromJson(jsonString, UserResponse::class.java)
        return(userResponse)
    } catch (e : RedirectResponseException){
        println("Catch1")
        println(e.response.status.description)
    }catch (e : Exception){
        println("Catch2")
        println(e.message)
    }
    return UserResponse(1,"s","s",2,"s","s")
}


class  Login :  AppCompatActivity() {
     @SuppressLint("ResourceAsColor")
     override  fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_login)
        val email = findViewById<EditText>(R.id.editTextEmail)
        val password = findViewById<EditText>(R.id.editTextPassword)
        val loginButton = findViewById<Button>(R.id.buttonLogin)
        val progressBar = findViewById<ProgressBar>(R.id.progressBar)
         val signNav = findViewById<TextView>(R.id.textViewSignUp)

        signNav.setOnClickListener{
            val intent = Intent(applicationContext, Register::class.java)
            startActivity(intent)
            finish()
        }
        loginButton.setOnClickListener{
            progressBar.visibility = View.VISIBLE
            val emails = email.text.toString()
            val userPassword = password.text.toString()
            if (emails.isEmpty() or userPassword.isEmpty()){
//                Toast.makeText(applicationContext,"E-Mail and Password Can Not Be Empty  ", Toast.LENGTH_SHORT).show()
               progressBar.visibility = View.GONE
                println("empty")

            }

            lifecycleScope.launch {
                withContext(Dispatchers.IO){
                    println("Last Log")
                    val resCall = logInCall(emails,userPassword)
                    println("avataaaaaaaaaar "+ resCall.avatar)
                    if (resCall.code == 200) {
                        // Create an Intent to start SecondActivity
                        val intent = Intent(applicationContext, HomeActivity::class.java)
                        intent.putExtra("avatar", resCall.avatar)
                        intent.putExtra("userId", resCall.id.toString())
                        intent.putExtra("userName", resCall.name)


                        println("Redirectttttttttttt")
                        startActivity(intent)
                        finish() // Optionally finish the current activity
                    }
                }

            }

        }

    }
}