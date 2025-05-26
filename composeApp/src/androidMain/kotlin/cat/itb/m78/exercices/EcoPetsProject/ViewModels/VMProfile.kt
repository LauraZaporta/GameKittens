package cat.itb.m78.exercices.EcoPetsProject.ViewModels

import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import cat.itb.m78.exercices.EcoPetsProject.DTOs.Employee
import cat.itb.m78.exercices.EcoPetsProject.DTOs.Pet
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
            0,
            0)
        //ask for employee's Pet API
        val employeePet = Pet(
            id = 1,
            animal = 3, // per exemple, un gat
            name = "Mixa",
            beingPat = false,
            hunger = mutableIntStateOf(0),
            prop = mutableIntStateOf(2), // suposem que és de la província 2
            petBeingPatUri = "https://example.com/images/mixa-being-pat.png",
            petImageUri = "https://example.com/images/mixa.png",
            hungerUri = "https://example.com/images/mixa-hungry.png",
            aLotOfHungerUri = "https://example.com/images/mixa-very-hungry.png",
            employeeId = "emp123"
        )

        user.value = UserProfile(
            userName = currentEmployee.userName,
            nameAndSurname = "${currentEmployee.name} ${currentEmployee.surname}",
            dni = currentEmployee.dni,
            phone = currentEmployee.phone,
            email = currentEmployee.email,
            petName = employeePet.name,
            petHunger = employeePet.hunger.value
        )

        if (user.value!!.petHunger < 3) isPetHungry.value = false
        else if (user.value!!.petHunger > 7) isPetHungry.value = true
        else isPetHungry.value = null
    }
}