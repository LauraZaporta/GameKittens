package cat.itb.m78.exercices.EcoPetsProject.Screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.net.toUri
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHost
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import cat.itb.m78.exercices.EcoPetsProject.DTOs.Task
import cat.itb.m78.exercices.EcoPetsProject.ViewModels.TasksListViewModel
import kotlinx.serialization.Serializable

@Serializable
data object ListTasksArguments


@Composable
fun ScreenListTasks(navigateToScreenAddTask: () -> Unit,
                    navigateToScreenDetailsTask: (Int) -> Unit)
{
    val viewModel = viewModel{ TasksListViewModel() }

    Column (modifier = Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally) {
        NavHost(navController = rememberNavController(), startDestination = ListTasksArguments) {
            composable<ListTasksArguments> {
                ScreenListTasksArguments(
                    tasks = viewModel.filteredList.value!!,
                    stringFilter = viewModel.filterString.value,
                    onUpdateFilter = viewModel::onUpdateFilter,
                    attributeToSearchBy = viewModel.attributeToSearchBy.value,
                    onUpdateAttributeFilter = { viewModel.onUpdateAttributeFilter() },
                    navigateToScreenAddTask = { navigateToScreenAddTask() },
                    navigateToScreenDetailsTask = { navigateToScreenDetailsTask(it) }
                )
            }
        }
    }
}

@Composable
fun ScreenListTasksArguments(
    tasks: List<Task>,
    stringFilter: String,
    onUpdateFilter:(String) -> Unit,
    attributeToSearchBy: Boolean, //When it's false search by tasks votes. When it's true search by tasks employee user name
    onUpdateAttributeFilter:() -> Unit,
    navigateToScreenAddTask:() -> Unit,
    navigateToScreenDetailsTask:(Int) -> Unit) {
    val notValidFilter = remember {mutableStateOf(false)} //Used with the search by tasks votes

    Column (horizontalAlignment = Alignment.CenterHorizontally) {
        Row {
            Button(
                onClick = { navigateToScreenAddTask() }
            ) {
                Text("Send Sustainable Task", textAlign = TextAlign.Center)
            }
            if (attributeToSearchBy) {//search by user name
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text("Search by User Name")
                    TextField(
                        value = stringFilter,
                        label = { Text(text = "") },
                        onValueChange = { onUpdateFilter(it) }
                    )
                }
            } else { //search by task votes
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text("Search by Task Votes")
                    TextField(
                        value = stringFilter,
                        label = { Text(text = "") },
                        onValueChange = { sF ->
                            if (sF.all { it.isDigit() }) {
                                notValidFilter.value = false
                            } else {
                                notValidFilter.value = true
                                onUpdateFilter(sF)
                            }
                        }
                    )
                    Text("The votes is a numeric value", color = Color.Red)
                }
            }
            IconButton(onClick = { onUpdateAttributeFilter() }) {
                Icon(Icons.Default.MoreVert, contentDescription = "Change search option")
            }
        }
        Text("Tasks List:", fontSize = 30.sp, fontWeight = FontWeight(800))
        LazyColumn(horizontalAlignment = Alignment.CenterHorizontally) {
            for (task in tasks) {
                item {
                    Box(modifier = Modifier.clickable { navigateToScreenDetailsTask(task.id) }) {
                        Text(" - ${task.title}", fontSize = 20.sp)
                        Text("   made by: ${task.employee.name}")
                        Spacer(modifier = Modifier.size(30.dp))
                    }
                }
            }
        }
    }
}