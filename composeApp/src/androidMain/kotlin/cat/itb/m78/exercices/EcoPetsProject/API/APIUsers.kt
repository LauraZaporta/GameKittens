package cat.itb.m78.exercices.EcoPetsProject.API

import io.ktor.client.HttpClient
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json

class APIUsers(id : String) {
    private val url = "https://eldenring.fanapis.com/api/sorceries?limit=100"
    private val urlDetail = "https://eldenring.fanapis.com/api/sorceries/%s"
    private val client = HttpClient() {
        install(ContentNegotiation) {
            json(Json {
                ignoreUnknownKeys = true
            })
        }
    }
}