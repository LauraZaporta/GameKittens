package cat.itb.m78.exercices.EcoPetsProject.ViewModels

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import cat.itb.m78.exercices.EcoPetsProject.API.APIUsers
import cat.itb.m78.exercices.EcoPetsProject.settings
import com.russhwolf.settings.get
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
                    val userInfo = APIUsers().detailUser(settings.getStringOrNull("key").toString())
                    settings.putInt("points", userInfo.points)
                    navigateToNavigationApp()
                } catch (e: Exception) {
                    Log.e("LoginError", "Login failed", e)
                    loginMessage.value = "Login failed!"
                    validLogin.value = false
                }
            } else {
                loginMessage.value = "All fields are required!"
                validLogin.value = false
            }
        }
    }
}