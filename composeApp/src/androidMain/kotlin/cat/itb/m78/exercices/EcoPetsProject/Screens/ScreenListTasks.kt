package cat.itb.m78.exercices.EcoPetsProject.Screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

class Task(
    val titel: String,
    val description: String,
    val imageURI: String,
    val userName: String //Seleccionar el nom d'usuari de l'empleat a partir del seu ID a la BD
)

@Composable
fun ScreenTasks(
    tasks: List<Task>,
    navigateToAddTaskScreen:() -> Unit
){
    val displayTask = remember { mutableStateOf(false)}
    val taskToDisplay = remember { mutableStateOf(Task("", "", "", "")) }

    Column (horizontalAlignment = Alignment.CenterHorizontally){
        if (!displayTask.value) {
            Button(
                onClick = { navigateToAddTaskScreen() }
            ) {
                Text("Send Sustainable Task", textAlign = TextAlign.Center)
            }
            Text("Tasks List:", fontSize = 30.sp, fontWeight = FontWeight(800))
            LazyColumn(horizontalAlignment = Alignment.CenterHorizontally) {
                for (task in tasks) {
                    item() {
                        Text(" - ${task.titel}", fontSize = 20.sp)
                        Text("   made by: ${task.userName}")
                        Spacer(modifier = Modifier.size(30.dp))
                    }
                }
            }
        } else{

        }
    }
}


@Composable
fun PhoneNumber(title: String, phoneNumber: String) {
    val context = LocalContext.current
    Text(
        text = title,
        fontSize = 32.sp,
        color = Color.White,
        modifier = Modifier
            .fillMaxWidth()
            .clickable {  },
        textAlign = TextAlign.Center
    )
}