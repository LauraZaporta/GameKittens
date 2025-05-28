package cat.itb.m78.exercices.EcoPetsProject.API

import cat.itb.m78.exercices.EcoPetsProject.Others.apiBaseUrl
import cat.itb.m78.exercices.EcoPetsProject.settings
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.get
import io.ktor.client.request.post
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.SerialName
import android.util.Base64
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import io.ktor.client.request.*
import io.ktor.http.ContentType
import io.ktor.http.HttpHeaders
import io.ktor.http.contentType
import kotlinx.serialization.json.jsonObject
import kotlinx.serialization.json.jsonPrimitive

@Serializable
data class UserData(
    @SerialName("id") val id: String,
    @SerialName("username") val username: String,
    @SerialName("name") val name: String,
    @SerialName("surename") val surname: String,
    @SerialName("dni") val dni: String,
    @SerialName("email") val email: String,
    @SerialName("phoneNumber") val phone: String,
    @SerialName("points") val points: Int,
    @SerialName("money") val money: Int
)

@Serializable
data class LoginRequest(val email: String, val password: String)
@Serializable
data class LoginResponse(val token: String)

class APIUsers() {
    private val controller = "User"

    private val client = HttpClient() {
        install(ContentNegotiation) {
            json(Json {
                ignoreUnknownKeys = true
            })
        }
    }

    private fun decodeJwtPayload(token: String): String {
        val parts = token.split(".")
        if (parts.size != 3) throw IllegalArgumentException("Invalid JWT token format")
        val payload = parts[1]

        // Afegim padding si cal (Base64url -> Base64 compatible)
        val fixedPayload = when (payload.length % 4) {
            2 -> "$payload=="
            3 -> "$payload="
            else -> payload
        }

        val decodedBytes = Base64.decode(fixedPayload, Base64.URL_SAFE or Base64.NO_WRAP)
        return String(decodedBytes, Charsets.UTF_8)
    }


    // Login
    suspend fun login(email: String, password: String): String {
        val token: String = client.post("$apiBaseUrl/Auth/login") {
            contentType(ContentType.Application.Json)
            setBody(LoginRequest(email, password))
        }.body()

        settings.putString("token", token)
        val payloadJson = decodeJwtPayload(token)
        val jsonObject = Json.parseToJsonElement(payloadJson).jsonObject
        val userId = jsonObject["http://schemas.xmlsoap.org/ws/2005/05/identity/claims/nameidentifier"]?.jsonPrimitive?.content

        if (userId != null) {
            settings.putString("key", userId)
        }

        return token
    }

    // Get functions
    suspend fun listUsers(): List<UserData> {
        val token: String? = settings.getStringOrNull("token")
        return client.get("$apiBaseUrl/$controller") {
            headers {
                bearerAuth(token!!)
            }
        }.body()
    }
    suspend fun detailUser(id: String): UserData {
        val token: String? = settings.getStringOrNull("token")
        return client.get("$apiBaseUrl/$controller/$id") {
            headers {
                append(HttpHeaders.Authorization, "Bearer $token")
            }
        }.body()
    }
}