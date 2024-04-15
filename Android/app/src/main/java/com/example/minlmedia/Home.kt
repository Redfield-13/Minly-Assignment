package com.example.minlmedia



import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.PopupMenu
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.launch
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso









class HomeActivity : AppCompatActivity() {
    @SuppressLint("MissingInflatedId")
    override  fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        lifecycleScope.launch {
            try {
                val data = getImages("https://k8fm9r7b-3456.uks1.devtunnels.ms/getImages") // Suspended API call
                val recyclerView: RecyclerView = findViewById(R.id.recyclerView)
                recyclerView.layoutManager = LinearLayoutManager(this@HomeActivity)
                val adapter = CardAdapter(data.reversed())
                recyclerView.adapter = adapter
            } catch (e: Exception) {
                // Handle API call error
                Log.e("API_CALL", "Error fetching data: ${e.message}")
            }

        }

        val recyclerView: RecyclerView = findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)




        val receivedAvatar = intent.getStringExtra("avatar")
        val receivedId = intent.getStringExtra("userId")
        val receivedName = intent.getStringExtra("userName")

        val avatarWidget : ImageView = findViewById(R.id.avatarImageView)
        println("urlllllll " + Uri.parse(receivedAvatar) )

        Picasso.get().load(receivedAvatar).placeholder(R.drawable.avatar_placeholder).error(R.drawable.avatar_placeholder).into(avatarWidget)



        val menuIconImageView: ImageView = findViewById(R.id.menuIconImageView)

        val popupMenu = PopupMenu(this, menuIconImageView)
        popupMenu.menuInflater.inflate(R.menu.popup_menu, popupMenu.menu)

        popupMenu.setOnMenuItemClickListener { item: MenuItem ->
            when (item.itemId) {
                R.id.menu_home -> {

                    true
                }
                R.id.menu_uploads -> {
                    val intent = Intent(applicationContext, UploadActivity::class.java)
                    intent.putExtra("avatar", receivedAvatar)
                    intent.putExtra("uploadId", receivedId)

                    println("RedirecttttttttttttUplooooooooooad")
                    startActivity(intent)
                    finish() // Optionally finish the current activity
                    true
                }
                else -> false
            }
        }
        menuIconImageView.setOnClickListener {
            popupMenu.show()
        }
    }
}
