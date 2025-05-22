package cat.itb.m78.exercices.EcoPetsProject.ViewModels

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import cat.itb.m78.exercices.EcoPetsProject.DTOs.Employee

class SendPointsViewModel : ViewModel() {
    val employees: MutableState<List<Employee>?> = mutableStateOf(null)
    val namesList = mutableListOf("")
    val coinsToSend = mutableStateOf("")
    val empId = mutableStateOf("")
    val userName = mutableStateOf("")
    val pointsSharedSuccess = mutableStateOf(false)

    init {
        //select a list of employees
        employees.value = listOf(
            Employee("1", "jose", "", "", "", "0", "", "", 2, 0),
            Employee("2", "paco", "", "", "", "0", "", "", 4, 0)
        )
        for (emp in employees.value!!){
            namesList.add(emp.userName)
        }
    }

    fun selectEmployeeByUserName(uN: String){
        userName.value = uN
        for (emp in employees.value!!){
            if (userName.value == emp.userName){
                empId.value = emp.id
            }
        }
    }
    fun updatePointsToSendAmount(totalAmount: String){
        if (totalAmount.all { it.isDigit() }) coinsToSend.value = totalAmount
    }

    fun addPointsToTheUser(){
        //select the user in the DB
        //add an if that makes shore that the current user have more or equal points than what it wants to share
        //select the employee with id = userId in the DB and, if it exists, update its points
        //update the user points
        pointsSharedSuccess.value = true //only if the points update was done
    }

    fun acceptYouSharedThePoints(){
        pointsSharedSuccess.value = false
        userName.value = ""
        coinsToSend.value = ""
    }
}