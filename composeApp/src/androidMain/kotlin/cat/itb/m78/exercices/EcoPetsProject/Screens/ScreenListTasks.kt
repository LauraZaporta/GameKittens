package cat.itb.m78.exercices.EcoPetsProject.Screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.material3.IconButton
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import cat.itb.m78.exercices.EcoPetsProject.DTOs.Task
import cat.itb.m78.exercices.EcoPetsProject.Others.ColorConstants
import cat.itb.m78.exercices.EcoPetsProject.Others.GenerateIndeterminateCircularIndicator
import cat.itb.m78.exercices.EcoPetsProject.Others.getFontFamily
import cat.itb.m78.exercices.EcoPetsProject.ViewModels.VMListTasks

@Composable
fun ScreenListTasks(navigateToScreenAddTask: () -> Unit,
                    navigateToScreenDetailsTask: (Int) -> Unit)
{
    val viewModel = viewModel{ VMListTasks() }

    if (viewModel.loading.value){
        Column(modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center)
        {
            GenerateIndeterminateCircularIndicator(ColorConstants.colorWhiteNotWhite,
                ColorConstants.colorAncientPink)
        }
    } else {
        ScreenListTasksArguments(
            tasks = viewModel.sortedTasksList.value,
            stringFilter = viewModel.filterString,
            navigateToScreenAddTask = { navigateToScreenAddTask() },
            navigateToScreenDetailsTask = { navigateToScreenDetailsTask(it) },
            like = viewModel::like,
            dislike = viewModel::dislike,
            sort = viewModel::sort,
            sortIcon = viewModel.sortIcon.value
        )
    }
}

@Composable
fun ScreenListTasksArguments(
    tasks: List<Task>,
    stringFilter: MutableState<String>,
    navigateToScreenAddTask:() -> Unit,
    navigateToScreenDetailsTask:(Int) -> Unit,
    like:(Task) -> Unit,
    dislike:(Task) -> Unit,
    sort:() -> Unit,
    sortIcon: ImageVector)
{

    val filteredTasks = tasks.filter {it.employee.userName.contains(stringFilter.value, ignoreCase = true)}

    Box(modifier = Modifier.fillMaxSize())
    {
        Button(
        modifier = Modifier.padding(10.dp).align(Alignment.BottomEnd),
        onClick = { navigateToScreenAddTask() },
        shape = RoundedCornerShape(10.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = ColorConstants.colorCottonPink)
    ) {
        Text("Add task",
            color = ColorConstants.colorGrey,
            fontSize = 3.em,
            fontFamily = getFontFamily()
        )
    }
        Column (horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxSize().padding(top = 40.dp))
        {
            Text("Tasks List",
                fontSize = 30.sp,
                fontFamily = getFontFamily(),
                color = ColorConstants.colorGrey
            )
            Row{
                OutlinedTextField(
                    modifier = Modifier.padding(top = 15.dp, bottom = 5.dp),
                    value = stringFilter.value,
                    label = { Text("Search by username") },
                    onValueChange = { stringFilter.value = it }
                )
                Spacer(Modifier.width(15.dp))
                Column{
                    IconButton(onClick = sort, modifier = Modifier.padding(top = 25.dp))
                    {
                        Icon(
                            imageVector = sortIcon, contentDescription = null,
                            modifier = Modifier.size(50.dp),
                            tint = Color.Black,
                        )
                    }
                }
            }
            Spacer(Modifier.height(20.dp))
            LazyColumn(horizontalAlignment = Alignment.CenterHorizontally) {
                if (filteredTasks.isEmpty()){
                    item{
                        Spacer(Modifier.height(15.dp))
                        Text("This employee doesn't have tasks",
                            fontFamily = getFontFamily(),
                            color = ColorConstants.colorGrey)
                    }
                }
                items(filteredTasks.chunked(2)) { rowTasks ->
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceEvenly
                    ) {
                        rowTasks.forEach { task ->
                            Card(
                                modifier = Modifier
                                    .width(150.dp)
                                    .height(120.dp)
                                    .clickable(
                                        enabled = true,
                                        onClickLabel = "Clickable card",
                                        onClick = { navigateToScreenDetailsTask(task.id) }
                                    )
                            ) {
                                Column(
                                    modifier = Modifier
                                        .fillMaxSize()
                                        .background(ColorConstants.colorCottonPink)
                                        .padding(8.dp),
                                    verticalArrangement = Arrangement.Center,
                                    horizontalAlignment = Alignment.CenterHorizontally
                                ) {
                                    Spacer(Modifier.height(5.dp))
                                    Text(
                                        task.title,
                                        color = ColorConstants.colorGrey,
                                        textAlign = TextAlign.Center,
                                        fontFamily = getFontFamily()
                                    )
                                    Spacer(Modifier.height(5.dp))
                                    Text(
                                        "Made by ${task.employee.userName}",
                                        color = ColorConstants.colorGrey,
                                        fontFamily = getFontFamily()
                                    )
                                    Spacer(Modifier.height(5.dp))
                                    Row(modifier = Modifier.fillMaxWidth(),
                                        horizontalArrangement = Arrangement.Center)
                                        {
                                        // S'haurà de modificar per a que canvii d'estat quan
                                        // hi hagi connexió amb l'API
                                        IconButton(onClick = { like(task) }) {
                                            Icon(Icons.Default.Check,
                                                contentDescription = null,
                                                tint = ColorConstants.colorGrey)
                                        }
                                            Text(
                                                task.votes.toString(),
                                                modifier = Modifier.padding(top = 16.dp),
                                                color = ColorConstants.colorGrey,
                                                fontFamily = getFontFamily(),
                                                fontWeight = FontWeight(500)
                                            )
                                        IconButton(onClick = { dislike(task) }) {
                                            Icon(Icons.Default.Close,
                                                contentDescription = null,
                                                tint = ColorConstants.colorGrey)
                                        }
                                    }
                                }
                            }
                        }
                    }
                    Spacer(Modifier.height(25.dp))
                }
            }
        }
    }
}