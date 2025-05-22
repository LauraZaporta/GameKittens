package cat.itb.m78.exercices.EcoPetsProject.ViewModels

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.lifecycle.ViewModel
import cat.itb.m78.exercices.EcoPetsProject.DTOs.Employee

class UserPoints(
    val userName: String,
    val points: String
)

class RankViewModel : ViewModel() {
    val employeesList: MutableState<List<Employee>?> = mutableStateOf(null)
    val usersPoints = mutableListOf(UserPoints("User Name", "Points"))
    val ranking = mutableListOf("")

    init {
        //asks for employees list to the API
        val orderList: MutableState<UserPoints?> = mutableStateOf(null)
        val rankLine = mutableStateOf("")

        if (employeesList.value == null) {
            usersPoints.add(UserPoints("Jose_Fina", "100"))
            usersPoints.add(UserPoints("PancracioElMarino", "1000"))
            usersPoints.add(UserPoints("EustaquioGarbanzos", "500"))
        } else {
            for (emp in employeesList.value!!) {
                usersPoints.add(UserPoints(emp.userName, emp.points.toString()))
            }
        }
        for (i in 1..<usersPoints.indices.last) {
            for (j in 2..usersPoints.indices.last) {
                if (usersPoints[j].points.toInt() > usersPoints[i].points.toInt()) {
                    orderList.value = usersPoints[j]
                    usersPoints[j] = usersPoints[i]
                    usersPoints[i] = orderList.value!!
                }
                orderList.value = null
            }
        }

        rankLine.value += usersPoints[0].userName
        for (i in 9..40) {
            rankLine.value += " "
        }
        rankLine.value += usersPoints[0].points
        ranking[0] = (rankLine.value)

        for (i in 1..usersPoints.indices.last){
            rankLine.value = ""
            for (j in usersPoints[i].userName.indices){
                rankLine.value += usersPoints[i].userName[j]
            }

            if (usersPoints[i].userName.length < 40) {
                for (j in usersPoints[i].userName.length..40) {
                    rankLine.value += ".."
                }
            }

            for (j in usersPoints[i].points.indices){
                rankLine.value += usersPoints[i].points[j]
            }
            ranking.add(rankLine.value)
        }
    }
}