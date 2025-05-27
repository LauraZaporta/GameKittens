package cat.itb.m78.exercices.EcoPetsProject.ViewModels

import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import cat.itb.m78.exercices.EcoPetsProject.DTOs.Employee
import cat.itb.m78.exercices.EcoPetsProject.DTOs.UserProfile

class VMProfile(userId: String?) : ViewModel(){
    val user = mutableStateOf<UserProfile?>(null)
    val isPetHungry = mutableStateOf<Boolean?>(null)

    init {
        //ask for employee with id API
        val currentEmployee = Employee("12345",
            "HMiku",
            "Hola",
            "Adéu",
            "78230984Z",
            "987654321",
            "@gmail.com",
            0)

        user.value = UserProfile(
            userName = currentEmployee.userName,
            nameAndSurname = "${currentEmployee.name} ${currentEmployee.surname}",
            dni = currentEmployee.dni,
            phone = currentEmployee.phone,
            email = currentEmployee.email,
        )
    }
}