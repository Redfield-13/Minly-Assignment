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
import io.ktor.client.request.forms.formData
import io.ktor.client.request.forms.submitFormWithBinaryData
import java.io.File


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
    val gson = Gson()
    println(jsonString)

}