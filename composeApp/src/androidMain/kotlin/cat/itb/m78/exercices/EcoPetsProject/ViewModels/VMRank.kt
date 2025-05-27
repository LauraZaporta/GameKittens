package cat.itb.m78.exercices.EcoPetsProject.ViewModels

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import cat.itb.m78.exercices.EcoPetsProject.API.APIUsers
import cat.itb.m78.exercices.EcoPetsProject.API.UserData
import cat.itb.m78.exercices.EcoPetsProject.DTOs.Employee
import cat.itb.m78.exercices.EcoPetsProject.DTOs.UserProfile
import cat.itb.m78.exercices.EcoPetsProject.DTOs.UserRank
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class VMRank : ViewModel() {
    val topUsers = mutableStateOf<List<UserRank>>(emptyList())
    val apiLoaded = mutableStateOf(false)

    init {
        //asks for top employees list to the API
        viewModelScope.launch(Dispatchers.Default) {
            val usersToMap = APIUsers().listUsers()
            mapAndTopEmployeesToUserRank(usersToMap)
            apiLoaded.value = true
        }
    }

    private fun mapAndTopEmployeesToUserRank(employees: List<UserData>){
        val topTenEmployees = employees.sortedByDescending { it.points }.take(10)
        topUsers.value = topTenEmployees.map { e ->
            UserRank(
                userName = e.username,
                points = e.points
            )
        }
    }
}