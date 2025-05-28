package cat.itb.m78.exercices.EcoPetsProject.ViewModels

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import cat.itb.m78.exercices.EcoPetsProject.settings

class VMPointsGeneral : ViewModel(){
    val userPoints = mutableStateOf(settings.getInt("points", 0))

    fun updatePoints(newPoints: Int) {
        settings.putInt("points", newPoints)
        userPoints.value = newPoints
    }

    fun subtractPoints(pointsToSubtract: Int) {
        val updatedPoints = userPoints.value - pointsToSubtract
        updatePoints(updatedPoints)
    }
}