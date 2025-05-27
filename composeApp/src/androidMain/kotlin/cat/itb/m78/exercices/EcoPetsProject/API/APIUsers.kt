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
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import io.ktor.client.request.*
import io.ktor.http.ContentType
import io.ktor.http.HttpHeaders
import io.ktor.http.contentType

@Serializable
data class UserData(
    // Falta id
    @SerialName("name") val name: String,
    @SerialName("surename") val surname: String,
    @SerialName("points") val points: Int,
    @SerialName("money") val money: Int
)

@Serializable
data class LoginRequest(val email: String, val password: String)
@Serializable
data class LoginResponse(val token: String)

class APIUsers() {

    private val client = HttpClient() {
        install(ContentNegotiation) {
            json(Json {
                ignoreUnknownKeys = true
            })
        }
    }

    // Login
    suspend fun login(email: String, password: String): String {
        val token: String = client.post("$apiBaseUrl/Auth/login") {
            contentType(ContentType.Application.Json)
            setBody(LoginRequest(email, password))
        }.body()

        return token
    }

    // Get functions
    suspend fun listUsers(): List<UserData> {
        val token: String? = settings.getStringOrNull("token")
        return client.get("$apiBaseUrl/User") {
            headers {
                append(HttpHeaders.Authorization, "Bearer $token")
            }
        }.body()
    }
    suspend fun detailUser(id: String): UserData {
        val token: String? = settings.getStringOrNull("token")
        return client.get("$apiBaseUrl/user/$id") {
            headers {
                append(HttpHeaders.Authorization, "Bearer $token")
            }
        }.body()
    }
}