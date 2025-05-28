package cat.itb.m78.exercices.EcoPetsProject.ViewModels

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import cat.itb.m78.exercices.EcoPetsProject.API.APIUsers
import cat.itb.m78.exercices.EcoPetsProject.DTOs.Employee
import cat.itb.m78.exercices.EcoPetsProject.settings
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class VMSendPoints : ViewModel() {
    private val userPoints = settings.getInt("points", 0)
    val employeesNames = mutableStateOf<List<String>>(emptyList())
    val employeeSelected = mutableStateOf(false)
    val pointsToSend = mutableStateOf("")
    val employees = mutableStateOf<List<Employee>>(emptyList())
    val chosenId = mutableStateOf("")
    val chosenUserName = mutableStateOf("")
    val pointsSharedSuccess = mutableStateOf<Boolean?>(null)

    init {
        //select the list of employees API
        viewModelScope.launch(Dispatchers.Default){
            val userList = APIUsers().listUsers()
            employees.value = userList.map{ u ->
                Employee(
                    id = u.id,
                    userName = u.username,
                    name = u.name,
                    surname = u.surname,
                    dni = u.dni,
                    phone = u.phone,
                    email = u.email,
                    points = u.points
                )
            }
        }
        employeesNames.value = employees.value.map { e -> e.userName }
    }

    fun addPointsToTheUser(vmPoints: VMPointsGeneral){
        viewModelScope.launch {
            val points = pointsToSend.value.toIntOrNull()
            val userList = APIUsers().listUsers()
            val usersIds : List<String> = userList.map{ it.id }
            val userExists = usersIds.contains(chosenId.value)
            if (points != null && points <= userPoints && userExists) {
                try {
                    APIUsers().sendPoints(
                        userId = settings.getString("key", ""),
                        targetUserId = chosenId.value,
                        pointsToSend = pointsToSend.value.toInt()
                        )
                    vmPoints.subtractPoints(points)
                    pointsSharedSuccess.value = true
                }
                catch(e: Exception){
                    pointsSharedSuccess.value = false
                }
            } else {
                pointsSharedSuccess.value = false
            }
        }
    }
}