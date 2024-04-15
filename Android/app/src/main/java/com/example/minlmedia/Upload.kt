package com.example.minlmedia



import android.annotation.SuppressLint
import android.content.Intent
import android.content.pm.PackageManager
import android.database.Cursor
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.MenuItem
import android.widget.Button
import android.widget.ImageView
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.PopupMenu
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.launch
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import com.squareup.picasso.Picasso
import io.ktor.client.HttpClient
import io.ktor.client.engine.cio.CIO
import io.ktor.client.plugins.kotlinx.serializer.KotlinxSerializer
import io.ktor.client.request.forms.formData
import io.ktor.client.request.forms.submitFormWithBinaryData
import io.ktor.client.request.post
import io.ktor.client.statement.HttpResponse
import io.ktor.client.statement.bodyAsText
import io.ktor.client.utils.EmptyContent.headers
import io.ktor.http.ContentType
import io.ktor.http.Headers
import io.ktor.http.HttpHeaders
import io.ktor.http.content.PartData
import io.ktor.http.headers
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.File
import kotlinx.serialization.json.Json
import kotlinx.serialization.Serializable




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








class UploadActivity : AppCompatActivity() {

    @SuppressLint("MissingInflatedId")
    private lateinit var uploadButto: Button
    private lateinit var imageViewTest: ImageView
    companion object {
        val IMAGE_REQUEST_CODE = 100
    }

    override  fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.upload)

        uploadButto = findViewById(R.id.uploadButton)
        imageViewTest = findViewById(R.id.testImage)


//        ------------------------------------- Grant Permissions -------------------------------




        //        --------------------------------------------------------------        ------------------------------
//
//
//        -------------------------------------------------------- Image Upload -----------------------------------



        uploadButto.setOnClickListener{
            lifecycleScope.launch {
                withContext(Dispatchers.IO){
                    pickImageGallerty()
                    println("uploooooooooad")
//                    upload()
                }
            }
        }

//        Open Studio


        //        --------------------------------------------------------------        ------------------------------
//
//
//        -------------------------------------------------------- Image Upload -----------------------------------

        val uploadAvatar = intent.getStringExtra("avatar")
        val uploadId = intent.getStringExtra("uploadId")
        println("looooooooooadddddddsssssssssss : "+uploadId)
        lifecycleScope.launch {
            try {
                val data = getImages("https://k8fm9r7b-3456.uks1.devtunnels.ms/getImages?authorID=$uploadId")
                println("idddddddddddddddddddddddddddddddddddddd : $uploadId")// Suspended API call
                val recyclerView: RecyclerView = findViewById(R.id.recyclerView)
                recyclerView.layoutManager = LinearLayoutManager(this@UploadActivity)
                val adapter = CardAdapter(data.reversed())
                recyclerView.adapter = adapter
            } catch (e: Exception) {
                // Handle API call error
                Log.e("API_CALL", "Error fetching data: ${e.message}")
            }

        }

        val recyclerView: RecyclerView = findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)

        val avatarWidget : ImageView = findViewById(R.id.avatarImageView)

        Picasso.get().load(uploadAvatar).placeholder(R.drawable.avatar_placeholder).error(R.drawable.avatar_placeholder).into(avatarWidget)
        val menuIconImageView: ImageView = findViewById(R.id.menuIconImageView)

        val popupMenu = PopupMenu(this, menuIconImageView)
        popupMenu.menuInflater.inflate(R.menu.popup_menu, popupMenu.menu)

        popupMenu.setOnMenuItemClickListener { item: MenuItem ->
            when (item.itemId) {
                R.id.menu_home -> {
                    val intent = Intent(applicationContext, HomeActivity::class.java)
                    intent.putExtra("avatar", uploadAvatar)
                    intent.putExtra("userId", uploadId)
                    println("RedirecttttttttttHooooooooommmmmmmme")
                    startActivity(intent)
                    finish() // Optionally finish the current activity
                    true
                }
                R.id.menu_uploads -> {
                    // Handle Uploads menu item click
                    true
                }
                else -> false
            }
        }
        menuIconImageView.setOnClickListener {
            popupMenu.show()
        }

    }

    private fun pickImageGallerty() {
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        startActivityForResult(intent, IMAGE_REQUEST_CODE)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == IMAGE_REQUEST_CODE && resultCode == RESULT_OK){
            println("imaaaaaageDataaaaa")
            var test: Uri? = data?.data
            println(data?.data)

            val projection = arrayOf(MediaStore.Images.Media.DATA)
            val cursor: Cursor? =
                data?.data?.let { contentResolver.query(it, projection, null, null, null) }
            val filePath: String? = cursor?.use {
                if (it.moveToFirst()) {
                    it.getString(it.getColumnIndexOrThrow(MediaStore.Images.Media.DATA))
                } else {
                    null
                }
            }
            cursor?.close()

            println(filePath)
            val READ_EXTERNAL_STORAGE_REQUEST_CODE = 123
            if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {

                    lifecycleScope.launch {
                    withContext(Dispatchers.IO){
                        println("uploooooooooad")
                        upload(filePath)
                    }
                }
            }else {
                // Request the permission
                ActivityCompat.requestPermissions(this, arrayOf(android.Manifest.permission.READ_EXTERNAL_STORAGE), READ_EXTERNAL_STORAGE_REQUEST_CODE)
            }
        }
    }
}
