package cat.itb.m78.exercices.EcoPetsProject.ViewModels

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import cat.itb.m78.exercices.EcoPetsProject.DTOs.Task

class TasksListViewModel : ViewModel(){
    val tasksList: MutableList<Task>? = null
    val filterString = mutableStateOf("")
    val atributToSearch = mutableStateOf( "userName" )

    fun onUpdateFilter(newFilterString: String){
        filterString.value = newFilterString
    }
}