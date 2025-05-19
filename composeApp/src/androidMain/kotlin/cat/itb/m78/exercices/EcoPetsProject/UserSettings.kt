package cat.itb.m78.exercices.EcoPetsProject

import com.russhwolf.settings.Settings

val settings: Settings = Settings()
val currentAppUser: String? = settings.getStringOrNull("key")