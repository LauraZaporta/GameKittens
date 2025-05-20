package cat.itb.m78.exercices.EcoPetsProject.Screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import cat.itb.m78.exercices.EcoPetsProject.DTOs.Task
import cat.itb.m78.exercices.EcoPetsProject.ViewModels.TaskDetailsViewModel
import coil3.compose.AsyncImage

@Composable
fun ScreenDetailsTask(idTask : Int){
    val viewModel = viewModel{ TaskDetailsViewModel() }
    viewModel.getTaskById(idTask)

    Column (modifier = Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally) {
        NavHost(navController = rememberNavController(), startDestination = ListTasksArguments) {
            composable<ListTasksArguments> {
                ScreenDetailsTaskArguments( task = viewModel.task.value!! )
            }
        }
    }
}

@Composable
fun ScreenDetailsTaskArguments(
    task: Task
){
    Column (horizontalAlignment = Alignment.CenterHorizontally) {
        Text(task.title, textAlign = TextAlign.Center, fontSize = 30.sp)
        Text("${task.votes}")
        Row (horizontalArrangement = Arrangement.Center) {
            AsyncImage(
                model = task.imageURI,
                contentDescription = "sustainable image",
                modifier = Modifier.size(400.dp, 200.dp)
            )
            Text("${task.employee.userName}: \n <<${task.description}>>")
        }
    }
}