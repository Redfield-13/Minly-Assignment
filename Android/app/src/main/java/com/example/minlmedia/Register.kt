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
                progressBar.visibility = View.GONE
                println("empty")

            }

            lifecycleScope.launch {
                withContext(Dispatchers.IO){
                    val resCall = signCall(userName,emails,userPassword,userConfimrPassword)
                    // Create an Intent to start SecondActivity
                    if (resCall.message == "USER CREATED"){
                        val intent = Intent(applicationContext, Login::class.java)
                        startActivity(intent)
                        finish() // Optionally finish the current activity
                    }
                }

            }

        }

    }
}