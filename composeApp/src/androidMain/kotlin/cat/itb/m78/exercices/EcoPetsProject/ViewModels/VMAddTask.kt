package cat.itb.m78.exercices.EcoPetsProject.ViewModels

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import cat.itb.m78.exercices.EcoPetsProject.DTOs.Employee
import cat.itb.m78.exercices.EcoPetsProject.DTOs.Task
import android.net.Uri

class VMAddTask : ViewModel(){
    val addingImage = mutableStateOf(false)
    val validNewTask = mutableStateOf<Boolean?>(null)
    val uri = mutableStateOf<Uri?>(null)
    val title = mutableStateOf<String?>(null)
    val desc = mutableStateOf<String?>(null)

    private fun fetchEmployee() : Employee{
        return Employee("12345",
            "HolaA",
            "Hola",
            "Adéu",
            "78230984Z",
            987654321,
            "@gmail.com",
            "hola12345",
            0,
            0)
    }
    fun addTask(){
        if (uri.value != null && title.value != null && desc.value != null){
            Task(1,
                0,
                title.value!!,
                desc.value!!,
                uri.value.toString(),
                fetchEmployee()
            )
            // Crida API per afegir tasca
            validNewTask.value = true
        }
        validNewTask.value = false
    }
}