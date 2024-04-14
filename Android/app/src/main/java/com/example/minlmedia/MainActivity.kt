package com.example.minlmedia

import android.graphics.Color
import android.os.Bundle
import android.util.Size
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import com.example.minlmedia.databinding.ActivityMainBinding
import kotlin.math.log
import io.ktor.client.*
import io.ktor.client.engine.cio.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import io.ktor.client.call.*
import io.ktor.client.request.forms.submitForm
import io.ktor.client.plugins.RedirectResponseException
import io.ktor.util.InternalAPI
import org.json.JSONArray
import org.json.JSONObject
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken


data class Item(val id: Int, var file_link: String, val author: String,  val likes: Int){
    fun getCourse_name(): String {
        return author
    }






    fun getCourse_image(): String {
        return file_link
    }

    fun setCourse_image(course_image: Int) {
        this.file_link = file_link
    }
}
suspend fun main() {
//    val jsonString = """[{"id":2,"file_link":"https://firebasestorage.googleapis.com/v0/b/minly-media.appspot.com/o/files%2Fphoto_2024-04-11_08-12-44.jpg?alt=media&token=e05f65f4-eed9-489b-b3c1-78c2c488affc","author":"Test Test","author_id":3,"mediaType":"image/jpeg","likes":2,"liked":"","unliked":""}]"""
//
//
//    val itemType = object : TypeToken<List<Item>>() {}.type
//    val items: List<Item> = Gson().fromJson(jsonString, itemType)
//
//    if (items.isNotEmpty()) {
//        val item = items[0]
//        println("id=${item.id}, file_link=${item.file_link}, author=${item.author}, author_id=${item.author_id}, mediaType=${item.mediaType}, likes=${item.likes}, liked=${item.liked}, unliked=${item.unliked}")
//    } else {
//        println("JSON array is empty.")
//    }

    val client = HttpClient(CIO)
    val baseUrl = "https://k8fm9r7b-3456.uks1.devtunnels.ms/getImages"
    try {
        val response: HttpResponse = client.request(baseUrl) {
            method = HttpMethod.Get
        }
        val jsonString : String = response.bodyAsText()
        val itemType = object : TypeToken<List<Item>>() {}.type
        val items: List<Item> = Gson().fromJson(jsonString, itemType)
        val item = items[0]
        println(items)

    } catch (e : RedirectResponseException){
        println("Catch1")
        println(e.response.status.description)
    }catch (e : Exception){
        println("Catch2")
        println(e.message)
    }

}