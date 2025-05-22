package cat.itb.m78.exercices.EcoPetsProject.ViewModels

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import cat.itb.m78.exercices.EcoPetsProject.DTOs.Employee
import cat.itb.m78.exercices.EcoPetsProject.DTOs.UserRank

class VMRank : ViewModel() {
    val topUsers = mutableStateOf<List<UserRank>>(emptyList())

    init {
        //asks for top employees list to the API
        val sampleEmployee = Employee("12345",
            "HMiku",
            "Hola",
            "Adéu",
            "78230984Z",
            "987654321",
            "@gmail.com",
            "hola12345",
            0,
            0)
        val sampleEmployee2 = Employee("12345",
            "PeppaPig",
            "Hola",
            "Adéu",
            "78230984Z",
            "987654321",
            "@gmail.com",
            "hola12345",
            0,
            0)

        MapEmployeeToUserRank(listOf(sampleEmployee, sampleEmployee2))
    }

    private fun MapEmployeeToUserRank(employees: List<Employee>){
        topUsers.value = employees.map { e ->
            UserRank(
                userName = e.userName,
                points = e.points
            )
        }
    }
}