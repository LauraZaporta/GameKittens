package cat.itb.m78.exercices.EcoPetsProject.ViewModels

import android.content.Context
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import cat.itb.m78.exercices.EcoPetsProject.DTOs.Employee
import android.net.Uri
import androidx.lifecycle.viewModelScope
import cat.itb.m78.exercices.EcoPetsProject.API.APITasks
import cat.itb.m78.exercices.EcoPetsProject.settings
import com.russhwolf.settings.get
import kotlinx.coroutines.launch

class VMAddTask : ViewModel(){
    val insertLoaded = mutableStateOf(true) // Quan és true no hi ha indicador de progés
    val addingImage = mutableStateOf(false)
    val validNewTask = mutableStateOf<Boolean?>(null)
    val uri = mutableStateOf<Uri?>(null)
    val title = mutableStateOf("")
    val desc = mutableStateOf("")

    fun addTask(context: Context){
        viewModelScope.launch {
            if (uri.value != null && title.value.isNotBlank() && desc.value.isNotBlank()) {
                insertLoaded.value = false
                val result = APITasks().insertTask(
                    context = context,
                    uri = uri.value!!,
                    title = title.value,
                    description = desc.value,
                    userId = settings.getStringOrNull("key").toString()
                )
                insertLoaded.value = true
                validNewTask.value = result
            } else {
                validNewTask.value = false
            }
        }
    }
}