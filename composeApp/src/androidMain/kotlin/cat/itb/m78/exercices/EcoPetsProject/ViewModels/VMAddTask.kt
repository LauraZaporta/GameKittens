package cat.itb.m78.exercices.EcoPetsProject.ViewModels

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import cat.itb.m78.exercices.EcoPetsProject.DTOs.Employee
import cat.itb.m78.exercices.EcoPetsProject.DTOs.Task
import cat.itb.m78.exercices.EcoPetsProject.settings
import coil3.Uri
import com.russhwolf.settings.get

class VMAddTask : ViewModel(){
    val newTask = mutableStateOf<Task?>(null)

    val uri = mutableStateOf<Uri?>(null)

    private fun fetchEmployee() : Employee{

    }
}