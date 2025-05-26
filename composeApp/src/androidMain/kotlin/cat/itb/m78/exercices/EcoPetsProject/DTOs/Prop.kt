package cat.itb.m78.exercices.EcoPetsProject.DTOs

import androidx.compose.runtime.MutableState

class Prop(
    val id: Int,
    val name: String,
    val imageUri: String,
    val price: Int,
    val unlocked: MutableState<Boolean>
)