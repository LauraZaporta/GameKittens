package cat.itb.m78.exercices.EcoPetsProject.ViewModels

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import cat.itb.m78.exercices.EcoPetsProject.DTOs.Employee
import cat.itb.m78.exercices.EcoPetsProject.DTOs.Pet
import cat.itb.m78.exercices.EcoPetsProject.DTOs.UserProfile

class ProfileViewModel : ViewModel(){
    val user: MutableState<UserProfile?> = mutableStateOf(null)
    val employees: MutableState<List<Employee>?> = mutableStateOf(null)
    val pets: MutableState<List<Pet>?> = mutableStateOf(null)
    val attributes = mutableListOf("", "", "", "", "", "", "")


    init {
        //asks for the employees and pets list to the API
    }

    fun getCurrentUser(/*userId: String*/){
        val userId = "" //it should arrive as an argument to the function, not be declared in it
        if (employees.value == null && pets.value == null){
            user.value = UserProfile(
                userName = "Paquito123",
                nameAndSurname = "Patines, Paco",
                dni = "12345678A",
                phone = "123456789",
                email = "hola@gamil.com",
                petName = "Sr Pantuflas",
                petHunger = "9"
            )

        } else {
            for (emp in employees.value!!) {
                if (emp.id == userId) {
                    attributes[0] = emp.userName
                    attributes[1] = "${emp.surname}, ${emp.name}"
                    attributes[2] = emp.dni
                    attributes[3] = emp.phone.toString()
                    attributes[4] = emp.email
                }
            }
            for (p in pets.value!!) {
                if (p.employeeId == userId) {
                    attributes[5] = p.name
                    attributes[6] = p.hunger.toString()
                }
            }
            user.value = UserProfile(
                userName = attributes[0],
                nameAndSurname = attributes[1],
                dni = attributes[2],
                phone = attributes[3],
                email = attributes[4],
                petName = attributes[5],
                petHunger = attributes[6]
            )
        }
    }
}