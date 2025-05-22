package cat.itb.m78.exercices.EcoPetsProject.ViewModels

import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import cat.itb.m78.exercices.EcoPetsProject.DTOs.Employee

class SendPointsViewModel : ViewModel() {
    private val pointsToSend = mutableIntStateOf(0)
    val employees = mutableStateOf<List<Employee>>(emptyList())
    val pointsToSendField = mutableStateOf("")
    val chosenUserName = mutableStateOf("")
    val pointsSharedSuccess = mutableStateOf(false)

    init {
        //select the list of employees API
        employees.value = listOf(
            Employee("1", "jose", "", "", "", "0", "", "", 2, 0),
            Employee("2", "paco", "", "", "", "0", "", "", 4, 0))
    }

    fun updatePointsToSendAmount(){
        if (pointsToSendField.value.all { it.isDigit() })
        {
            if (pointsToSendField.value.toInt() > 0) {
                pointsToSend.intValue = pointsToSendField.value.toInt()
            }
        }
    }

    fun addPointsToTheUser(){
        //select the user in the DB
        //add an if that makes shore that the current user have more or equal points than what it wants to share
        //select the employee with id = userId in the DB and, if it exists, update its points
        //update the user points
        pointsSharedSuccess.value = true //only if the points update was done
    }
}