package cat.itb.m78.exercices.EcoPetsProject.ViewModels

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import cat.itb.m78.exercices.EcoPetsProject.DTOs.Employee
import cat.itb.m78.exercices.EcoPetsProject.DTOs.Task

class VMListTasks : ViewModel(){
    private val tasksList = mutableStateOf<List<Task>>(emptyList()) //List get from the API
    private val orderedDesc = mutableStateOf(true)
    val sortedTasksList = mutableStateOf<List<Task>>(emptyList())
    val filterString = mutableStateOf("")
    val sortIcon = mutableStateOf(Icons.Default.KeyboardArrowDown)

    init {
        // Temp value
        val sampleEmployee = Employee("12345",
            "HMiku",
            "Hola",
            "Adéu",
            "78230984Z",
            987654321,
            "@gmail.com",
            "hola12345",
            0,
            0)
        val sampleEmployee2 = Employee("12345",
            "PeppaPig",
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
            votes = 3,
            title = "Nova funcionalitat",
            description = "Implementar la pantalla de login",
            imageURI = "https://example.com/image.jpg",
            employee = sampleEmployee
        )
        val sampleTask3 = Task(
            id = 1,
            votes = 7,
            title = "Nova funcionalitat",
            description = "Implementar la pantalla de login",
            imageURI = "https://example.com/image.jpg",
            employee = sampleEmployee2
        )
        tasksList.value = listOf(sampleTask, sampleTask2, sampleTask3)
        sortedTasksList.value = tasksList.value.sortedByDescending { it.votes }
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

    fun like(task: Task){ }
    fun dislike(task: Task){ }
}