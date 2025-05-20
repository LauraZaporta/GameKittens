package cat.itb.m78.exercices.EcoPetsProject.ViewModels

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import cat.itb.m78.exercices.EcoPetsProject.DTOs.Task

class TaskDetailsViewModel : ViewModel(){
    val task: MutableState<Task?> = mutableStateOf(null)
    val tasks: MutableState<List<Task>?> = mutableStateOf(null)

    init {
        //asks for tasks list to the API
    }

    fun getTaskById(taskId: Int){
        for (t in tasks.value!!){
            if (t.id == taskId){
                task.value = t
            }
        }
    }
}