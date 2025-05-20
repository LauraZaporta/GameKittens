package cat.itb.m78.exercices.EcoPetsProject.ViewModels

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import cat.itb.m78.exercices.EcoPetsProject.DTOs.Task

class TasksListViewModel : ViewModel(){
    val tasksList: MutableState<List<Task>?> = mutableStateOf(null) //List get from the API
    val filteredList: MutableState<List<Task>?> = mutableStateOf(null)
    val filterString = mutableStateOf("")
    val attributeToSearchBy = mutableStateOf( false )

    init {
        //tasksList.value = ...
        filteredList.value = tasksList.value
    }

    fun onUpdateFilter(newFilterString: String){
        filterString.value = newFilterString
        if (attributeToSearchBy.value){
            filteredList.value = tasksList.value?.filter {it.employee.userName.startsWith(filterString.value)}
        } else{
            filteredList.value = tasksList.value?.filter {it.votes.toString().startsWith(filterString.value)}
        }
    }
    fun onUpdateAttributeFilter(){
        attributeToSearchBy.value = !attributeToSearchBy.value
    }

    fun retrieveEntireList(){
        filterString.value = ""
        filteredList.value = tasksList.value
    }
}