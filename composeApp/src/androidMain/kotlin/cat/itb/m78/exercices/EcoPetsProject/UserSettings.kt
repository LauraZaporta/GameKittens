package cat.itb.m78.exercices.EcoPetsProject

import com.russhwolf.settings.Settings

val settings: Settings = Settings()
val currentUserPoints: Int? = settings.getIntOrNull("points")
val currentAppUserId: String? = settings.getStringOrNull("key")
val currentAppUserToken: String? = settings.getStringOrNull("token")