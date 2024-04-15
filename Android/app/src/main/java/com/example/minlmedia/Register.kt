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
data class UserResponses(
    val message: String,
)
suspend fun signCall(name: String,email:String, password:String, passwordConfirm: String): UserResponses {

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
        val userResponses: UserResponses = gson.fromJson(jsonString, UserResponses::class.java)
        return(userResponses)
    } catch (e : RedirectResponseException){
        println("Catch1")
        println(e.response.status.description)
    }catch (e : Exception){
        println("Catch2")
        println(e.message)
    }
    return UserResponses("s")
}


class  Register :  AppCompatActivity() {
    @SuppressLint("ResourceAsColor", "MissingInflatedId")
    override  fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_register)
        val email = findViewById<EditText>(R.id.editTextEmail)
        val password = findViewById<EditText>(R.id.editTextPassword)
        val loginButton = findViewById<Button>(R.id.buttonLogin)
        val progressBar = findViewById<ProgressBar>(R.id.progressBar)
        val passwordConfirm = findViewById<EditText>(R.id.confirmPassword)
        val name = findViewById<EditText>(R.id.editTextName)
        val loginNav = findViewById<TextView>(R.id.loginNow)


        loginNav.setOnClickListener{
            val intent = Intent(applicationContext, Login::class.java)
            startActivity(intent)
            finish()
        }


        loginButton.setOnClickListener{
            progressBar.visibility = View.VISIBLE
            val emails = email.text.toString()
            val userPassword = password.text.toString()
            val userConfimrPassword = passwordConfirm.text.toString()
            val userName = name.text.toString()
            if (emails.isEmpty() or userPassword.isEmpty()){
//                Toast.makeText(applicationContext,"E-Mail and Password Can Not Be Empty  ", Toast.LENGTH_SHORT).show()
                progressBar.visibility = View.GONE
                println("empty")

            }

            lifecycleScope.launch {
                withContext(Dispatchers.IO){
                    val resCall = signCall(userName,emails,userPassword,userConfimrPassword)
                    println("avataaaaaaaaaar "+ resCall)
                    if (resCall !== null) {
                        // Create an Intent to start SecondActivity
                        val intent = Intent(applicationContext, Login::class.java)
                        println("Redirectttttttttttt")
                        startActivity(intent)
                        finish() // Optionally finish the current activity
                    }
                }

            }

        }

    }
}