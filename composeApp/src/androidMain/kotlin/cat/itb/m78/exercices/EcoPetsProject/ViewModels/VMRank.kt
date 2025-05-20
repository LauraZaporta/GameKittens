package cat.itb.m78.exercices.EcoPetsProject.ViewModels

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import cat.itb.m78.exercices.EcoPetsProject.DTOs.Employee

class UserPoints(
    val userName: String,
    val points: String
)

class RankViewModel : ViewModel(){
    val employeesList: MutableState<List<Employee>?> = mutableStateOf(null)
    val usersPoints = mutableListOf(UserPoints("User Name", "Points"))

    init {
        //asks for employees list to the API
    }

    fun getRankList(){
        for (emp in employeesList.value!!){
            usersPoints.add(UserPoints(emp.userName, emp.points.toString()))
        }
    }
}