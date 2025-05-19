package cat.itb.m78.exercices.EcoPetsProject.Screens

import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage


@Composable
fun TaskInfo(
    task: Task
){
    AsyncImage(
        model = task.imageURI,
        contentDescription = "sustainable image",
        modifier = Modifier.size(400.dp, 200.dp)
    )
}