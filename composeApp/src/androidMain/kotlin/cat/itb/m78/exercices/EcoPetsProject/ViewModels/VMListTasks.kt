package cat.itb.m78.exercices.EcoPetsProject.ViewModels

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.lifecycle.ViewModel
import cat.itb.m78.exercices.EcoPetsProject.DTOs.Employee
import cat.itb.m78.exercices.EcoPetsProject.DTOs.Task

class TasksListViewModel : ViewModel(){
    val tasksList = mutableStateOf<List<Task>>(emptyList()) //List get from the API
    val filteredTasksList = mutableStateOf(tasksList) //List get from the API
    val filterString = mutableStateOf("")
    val orderedDesc = mutableStateOf(true)
    val sortIcon = mutableStateOf(Icons.Default.KeyboardArrowDown)

    init {
        // Temp value
        val sampleEmployee = Employee("12345",
            "HolaA",
            "Hola",
            "Adéu",
            "78230984Z",
            987654321,
            "@gmail.com",
            "hola12345",
            0,
            0)
        val sampleTask = Task(
            id = 1,
            votes = 5,
            title = "Nova funcionalitat",
            description = "Implementar la pantalla de login",
            imageURI = "https://example.com/image.jpg",
            employee = sampleEmployee
        )
        val sampleTask2 = Task(
            id = 1,
            votes = 5,
            title = "Nova funcionalitat",
            description = "Implementar la pantalla de login",
            imageURI = "https://example.com/image.jpg",
            employee = sampleEmployee
        )
        val sampleTask3 = Task(
            id = 1,
            votes = 5,
            title = "Nova funcionalitat",
            description = "Implementar la pantalla de login",
            imageURI = "https://example.com/image.jpg",
            employee = sampleEmployee
        )
        tasksList.value = listOf(sampleTask, sampleTask2, sampleTask3)
    }

    fun sort(tasksToSort : List<Task>) : List<Task>{
        return if (orderedDesc.value){
            orderedDesc.value = !orderedDesc.value
            sortIcon.value = Icons.Default.KeyboardArrowUp
            tasksToSort.sortedByDescending { it.votes }
        } else {
            orderedDesc.value = !orderedDesc.value
            sortIcon.value = Icons.Default.KeyboardArrowDown
            tasksToSort.sortedBy { it.votes }
        }
    }
    fun filterByName(){
        filteredTasksList.value = tasksList.value.filter {it.employee.userName.contains(stringFilter.value, ignoreCase = true)}
    }
    fun like(task: Task){ }
    fun dislike(task: Task){ }
}