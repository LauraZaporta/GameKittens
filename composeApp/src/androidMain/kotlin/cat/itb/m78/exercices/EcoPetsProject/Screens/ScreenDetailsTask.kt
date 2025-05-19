package cat.itb.m78.exercices.EcoPetsProject.Screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cat.itb.m78.exercices.EcoPetsProject.DTOs.Task
import coil3.compose.AsyncImage


@Composable
fun TaskInfo(
    task: Task
){
    Column (horizontalAlignment = Alignment.CenterHorizontally) {
        Text(task.title, textAlign = TextAlign.Center, fontSize = 30.sp)
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