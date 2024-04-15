package com.example.minlmedia

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext





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
                    if (resCall.code == 200) {
                        // Create an Intent to start SecondActivity
                        val intent = Intent(applicationContext, HomeActivity::class.java)
                        intent.putExtra("avatar", resCall.avatar)
                        intent.putExtra("userId", resCall.id.toString())
                        intent.putExtra("userName", resCall.name)
                        println("Redirecting : ")
                        startActivity(intent)
                        finish() // Optionally finish the current activity
                    }
                }

            }

        }

    }
}