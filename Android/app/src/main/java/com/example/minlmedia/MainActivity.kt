package com.example.minlmedia

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.util.Size
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import com.example.minlmedia.databinding.ActivityMainBinding
import kotlin.math.log

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        val email = findViewById<EditText>(R.id.editTextUsername)
        val password = findViewById<EditText>(R.id.editTextPassword)

        email.setTextColor(Color.GREEN)

//        Getting Input From Users With EditText

        var useremail = email.text.toString()
        var userpassword = password.text.toString()
        Log.v("Email", "The User Enters :  $useremail")



    }
}