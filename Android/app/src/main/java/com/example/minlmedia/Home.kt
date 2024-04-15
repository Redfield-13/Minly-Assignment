package com.example.minlmedia



import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.PopupMenu
import androidx.lifecycle.lifecycleScope
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import io.ktor.client.HttpClient
import io.ktor.client.engine.cio.CIO
import io.ktor.client.plugins.RedirectResponseException
import io.ktor.client.request.request
import io.ktor.client.statement.HttpResponse
import io.ktor.client.statement.bodyAsText
import io.ktor.http.HttpMethod
import kotlinx.coroutines.launch
import kotlinx.serialization.Serializable
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.squareup.picasso.Picasso

@Serializable
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

data class CardItem(
    val imageUrl: String,
    val title: String
)

class CardAdapter(private val items: List<Item>) :
    RecyclerView.Adapter<CardAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.card_layout, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = items[position]
        holder.bind(item)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val imageView: ImageView = itemView.findViewById(R.id.imageView)
        private val textViewTitle: TextView = itemView.findViewById(R.id.textViewTitle)


        fun bind(item: Item) {
            Glide.with(itemView.context)
                .load(item.file_link)
                .placeholder(R.drawable.navlogo) // Placeholder image while loading
                .error(R.drawable.navlogo) // Error image if loading fails
                .into(imageView)

            textViewTitle.text = "Posted By : "+item.author
        }
    }
}

suspend fun getImages(url : String) : List<Item> {

    val client = HttpClient(CIO)
    val baseUrl = url
    try {
        val response: HttpResponse = client.request(baseUrl) {
            method = HttpMethod.Get
        }
        val jsonString : String = response.bodyAsText()
        val itemType = object : TypeToken<List<Item>>() {}.type
        val items: List<Item> = Gson().fromJson(jsonString, itemType)
        val item = items[0]
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
        println("hoooooooommmmmmmmmmmmmeeeeeee id" + receivedId)
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
