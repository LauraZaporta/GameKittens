package cat.itb.m78.exercices.EcoPetsProject.ViewModels

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import cat.itb.m78.exercices.EcoPetsProject.DTOs.Employee
import cat.itb.m78.exercices.EcoPetsProject.DTOs.Task

class VMDetailsTask(id:Int) : ViewModel(){
    val task = mutableStateOf<Task?>(null)

    init {
        //asks for task with id to the API
        val sampleEmployee = Employee("12345",
            "HMiku",
            "Hola",
            "Adéu",
            "78230984Z",
            "987654321",
            "@gmail.com",
            "hola12345",
            0,
            0)
        val sampleTask = Task(
            id = 1,
            votes = 5,
            title = "Nova funcionalitat",
            description = "Implementar la pantalla de login",
            imageURI = "https://vignette.wikia.nocookie.net/dragonage/images/f/f0/Masterdennet.png/revision/latest/scale-to-width-down/350?cb=20170830213930",
            employee = sampleEmployee
        )
        task.value = sampleTask
    }

    fun like(){ }
    fun dislike(){ }
}