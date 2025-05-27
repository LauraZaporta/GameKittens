package cat.itb.m78.exercices.EcoPetsProject.ViewModels

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import cat.itb.m78.exercices.EcoPetsProject.DTOs.Employee

class VMSendPoints : ViewModel() {
    val employeesNames = mutableStateOf<List<String>>(emptyList())
    val employeeSelected = mutableStateOf(false)
    val pointsToSend = mutableStateOf("")
    val employees = mutableStateOf<List<Employee>>(emptyList())
    val chosenUserName = mutableStateOf("")
    val pointsSharedSuccess = mutableStateOf<Boolean?>(null)

    init {
        //select the list of employees API
        employees.value = listOf(
            Employee("1", "Peppa", "", "", "", "0", "", 2),
            Employee("2", "HMikua", "", "", "", "0", "", 4),
            Employee("2", "Ratotaaaaaaaaaa", "", "", "", "0", "", 4),
        )
        employeesNames.value = employees.value.map { e -> e.userName }
    }

    fun addPointsToTheUser(){
        //select the user in the DB
        //add an if that makes shore that the current user have more or equal points than what it wants to share
        //select the employee with id = userId in the DB and, if it exists, update its points
        //update the user points
        val points = pointsToSend.value.toIntOrNull()
        val userExists = employeesNames.value.contains(chosenUserName.value)
        //Amb l'id del currentUser es comprova si l'usuari té suficients punts. Es canvia la condició
        //de points > 0
        if (points != null && points > 0 && userExists) {
            pointsSharedSuccess.value = true
        } else {
            pointsSharedSuccess.value = false
        }
    }
}