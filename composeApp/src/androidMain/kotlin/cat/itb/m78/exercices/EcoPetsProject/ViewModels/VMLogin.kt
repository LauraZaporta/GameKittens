package cat.itb.m78.exercices.EcoPetsProject.ViewModels

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import cat.itb.m78.exercices.EcoPetsProject.API.APIUsers
import cat.itb.m78.exercices.EcoPetsProject.settings
import kotlinx.coroutines.launch

class VMLogin: ViewModel(){
    val email = mutableStateOf("")
    val password = mutableStateOf("")
    val passwordShown = mutableStateOf(false)
    private val validLogin = mutableStateOf<Boolean?>(null)
    val loginMessage = mutableStateOf<String?>(null)

    fun changePasswordVisibility(){
        passwordShown.value = !passwordShown.value
    }
    fun login(navigateToNavigationApp: () -> Unit) {
        viewModelScope.launch {
            if (email.value.isNotBlank() && password.value.isNotBlank()) {
                try {
                    val token = APIUsers().login(email.value, password.value)

                    // Save the token
                    settings.putString("token", token)

                    loginMessage.value = "Login successful!"
                    validLogin.value = true
                    navigateToNavigationApp()
                } catch (e: Exception) {
                    loginMessage.value = "Login failed! ${e.message}"
                    validLogin.value = false
                }
            } else {
                loginMessage.value = "All fields are required!"
                validLogin.value = false
            }
        }
    }
}