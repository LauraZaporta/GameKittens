package cat.itb.m78.exercices.EcoPetsProject.ViewModels

import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import cat.itb.m78.exercices.EcoPetsProject.API.APIUsers
import cat.itb.m78.exercices.EcoPetsProject.DTOs.Employee
import cat.itb.m78.exercices.EcoPetsProject.DTOs.UserProfile
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class VMProfile(userId: String?) : ViewModel(){
    val user = mutableStateOf<UserProfile?>(null)

    init {
        //ask for employee with id API
        viewModelScope.launch(Dispatchers.Default) {
            val userToMap = APIUsers().detailUser(userId.toString())
            user.value = UserProfile(
                userName = userToMap.username,
                nameAndSurname = userToMap.name,
                dni = userToMap.dni,
                phone = userToMap.phone,
                email = userToMap.email
            )
        }
    }
}