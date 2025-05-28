package cat.itb.m78.exercices.EcoPetsProject.ViewModels

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import cat.itb.m78.exercices.EcoPetsProject.API.APITasks
import cat.itb.m78.exercices.EcoPetsProject.API.APIUsers
import cat.itb.m78.exercices.EcoPetsProject.DTOs.Employee
import cat.itb.m78.exercices.EcoPetsProject.DTOs.Task
import cat.itb.m78.exercices.EcoPetsProject.settings
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class VMListTasks : ViewModel(){
    private val tasksList = mutableStateOf<List<Task>>(emptyList()) //List get from the API
    private val orderedDesc = mutableStateOf(true)
    val loading = mutableStateOf(true)
    val sortedTasksList = mutableStateOf<List<Task>>(emptyList())
    val filterString = mutableStateOf("")
    val sortIcon = mutableStateOf(Icons.Default.KeyboardArrowDown)

    init {
        viewModelScope.launch(Dispatchers.Default){
            loadTasks()
            loading.value = false
        }
    }

    fun sort(){
        return if (orderedDesc.value){
            val descSortedList = tasksList.value.sortedBy { it.votes }
            orderedDesc.value = !orderedDesc.value
            sortIcon.value = Icons.Default.KeyboardArrowUp
            sortedTasksList.value = descSortedList
        } else {
            val ascSortedList = tasksList.value.sortedByDescending { it.votes }
            orderedDesc.value = !orderedDesc.value
            sortIcon.value = Icons.Default.KeyboardArrowDown
            sortedTasksList.value = ascSortedList
        }
    }

    fun like(task: Task){
        viewModelScope.launch {
            loading.value = true
            APITasks().likeTask(task.id, task.employee.id)
            loadTasks()
            loading.value = false
        }
    }
    fun dislike(task: Task){
        viewModelScope.launch {
            loading.value = true
            APITasks().dislikeTask(task.id, task.employee.id)
            loadTasks()
            loading.value = false
        }
    }

    private suspend fun loadTasks(){
        val userAPI = APIUsers().detailUser(settings.getStringOrNull("key").toString())
        val userMapped = Employee(
            id = userAPI.id,
            name = userAPI.name,
            userName = userAPI.username,
            surname = userAPI.surname,
            dni = userAPI.dni,
            phone = userAPI.phone,
            email = userAPI.email,
            points = userAPI.points
        )
        val tasks = APITasks().listTasks()
        tasksList.value = tasks.map { t ->
            Task(
                id = t.id,
                votes = t.votes,
                title = t.title,
                description = t.desc.toString(),
                imageURI = t.image,
                employee = userMapped
            )
        }
        sortedTasksList.value = tasksList.value.sortedByDescending { it.votes }
    }
}