package cat.itb.m78.exercices.EcoPetsProject.Screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cat.itb.m78.exercices.EcoPetsProject.DTOs.Task

@Composable
fun ScreenTasks(
    tasks: List<Task>,
    filterString: String,
    onUpdateFilter:(String) -> Unit,
    navigateToAddTaskScreen:() -> Unit,
    navigateToTaskInfo: (Int) -> Unit
){
    Column (horizontalAlignment = Alignment.CenterHorizontally){
        Row{
            TextField(
                value = filterString,
                label = { Text(text = "") },
                onValueChange = { onUpdateFilter(it) }
            )
            Button(
                onClick = { navigateToAddTaskScreen() }
            ) {
                Text("Send Sustainable Task", textAlign = TextAlign.Center)
            }
        }
        Text("Tasks List:", fontSize = 30.sp, fontWeight = FontWeight(800))
        LazyColumn(horizontalAlignment = Alignment.CenterHorizontally) {
            for (task in tasks) {
                item {
                    Box (modifier = Modifier.clickable { navigateToTaskInfo(task.id) }) {
                        Text(" - ${task.title}", fontSize = 20.sp)
                        Text("   made by: ${task.employee.userName}")
                        Spacer(modifier = Modifier.size(30.dp))
                    }
                }
            }
        }
    }
}