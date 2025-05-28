package cat.itb.m78.exercices.EcoPetsProject.API

import android.content.Context
import android.net.Uri
import cat.itb.m78.exercices.EcoPetsProject.Others.apiBaseUrl
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.forms.formData
import io.ktor.client.request.forms.submitFormWithBinaryData
import io.ktor.client.request.get
import io.ktor.http.Headers
import io.ktor.http.HttpHeaders
import io.ktor.http.isSuccess
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import java.io.File

@Serializable
data class TaskData(
    @SerialName("id") val id: Int,
    @SerialName("validationVotes") val votes: Int,
    @SerialName("title") val name: String,
    @SerialName("description") val surname: String?,
    @SerialName("imageURL") val image: String,
    @SerialName("userId") val userId: String,
    @SerialName("userName") val userName: String
)

@Serializable
data class InsertTaskData(
    val title: String,
    val description: String,
    //...
)

class APITasks() {
    private val controller = "STask"

    private val client = HttpClient() {
        install(ContentNegotiation) {
            json(Json {
                ignoreUnknownKeys = true
            })
        }
    }
    private fun uriToFile(context: Context, uri: Uri): File {
        val inputStream = context.contentResolver.openInputStream(uri)
        val tempFile = File.createTempFile("upload", ".jpg", context.cacheDir)
        tempFile.outputStream().use { inputStream?.copyTo(it) }
        return tempFile
    }

    // Get functions
    suspend fun listTasks(): List<TaskData> {
        return client.get("$apiBaseUrl/$controller").body()
    }

    suspend fun detailTask(id: Int): TaskData {
        return client.get("$apiBaseUrl/$controller/$id").body()
    }

    // Insert function
    suspend fun insertTask(
        context: Context,
        uri: Uri,
        title: String,
        description: String,
        userId: String
    ): Boolean {
        return try {
            val file = uriToFile(context, uri)

            val response = client.submitFormWithBinaryData(
                url = "$apiBaseUrl/$controller",
                formData = formData {
                    append("Title", title)
                    append("Description", description)
                    append("UserId", userId)
                    append("ImageURL", file.readBytes(), Headers.build {
                        append(HttpHeaders.ContentType, "image/jpeg")
                        append(HttpHeaders.ContentDisposition, "filename=\"${file.name}\"")
                    })
                }
            )
            response.status.isSuccess()
        } catch (e: Exception) {
            e.printStackTrace()
            false
        }
    }
}