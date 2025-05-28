package cat.itb.m78.exercices.EcoPetsProject.ViewModels

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

class VMDetailsTask(private val idTask: Int) : ViewModel(){
    val task = mutableStateOf<Task?>(null)
    val loading = mutableStateOf(true)

    init {
        viewModelScope.launch(Dispatchers.Default){
            loadTask()
            loading.value = false
        }
    }

    fun like(){
        viewModelScope.launch {
            loading.value = true
            APITasks().likeTask(task.value!!.id, task.value!!.employee.id)
            loadTask()
            loading.value = false
        }
    }
    fun dislike(){
        viewModelScope.launch {
            loading.value = true
            APITasks().dislikeTask(task.value!!.id, task.value!!.employee.id)
            loadTask()
            loading.value = false
        }
    }

    private suspend fun loadTask(){
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
        val t = APITasks().detailTask(idTask)
        task.value = Task(
            id = t.id,
            votes = t.votes,
            title = t.title,
            description = t.desc.toString(),
            imageURI = t.image,
            employee = userMapped
        )
    }
}