package cat.itb.m78.exercices.EcoPetsProject.ViewModels

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel

class VMLogin: ViewModel(){
    val email = mutableStateOf("")
    val password = mutableStateOf("")
    val passwordShown = mutableStateOf(false)
    private val validLogin = mutableStateOf<Boolean?>(null)
    val loginMessage = mutableStateOf<String?>(null)

    fun changePasswordVisibility(){
        passwordShown.value = !passwordShown.value
    }
    fun login(navigateToNavigationApp: () -> Unit){
        // Codi a desenvolupar depenent de l'API
        if (email.value != "" && password.value != ""){
            validLogin.value = true
            loginMessage.value = "Login successful!"
            // S'assigna als settings l'id de l'usuari i es continua a l'app
            navigateToNavigationApp()
        }
        else {
            loginMessage.value = "All fields are required!"
            validLogin.value = false
        }
        
    }
}