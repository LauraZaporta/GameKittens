package cat.itb.m78.exercices.EcoPetsProject.Screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import cat.itb.m78.exercices.EcoPetsProject.ViewModels.SendPointsViewModel

@Composable
fun ScreenSendPoints(){
    val viewModel = viewModel{ SendPointsViewModel() }

    ScreenSendPointsArguments(
        userName = viewModel.userName.value,
        userNamesList = viewModel.namesList,
        coinsToSend = viewModel.coinsToSend.value,
        selectEmployeeByUserName = { viewModel.selectEmployeeByUserName(it) },
        updatePointsToSendAmount = { viewModel.updatePointsToSendAmount(it) },
        addPointsToTheUser = { viewModel.addPointsToTheUser() }

    )
}

@Composable
fun ScreenSendPointsArguments(
    userName: String,
    userNamesList: List<String>,
    coinsToSend: String,
    selectEmployeeByUserName: (String) -> Unit,
    updatePointsToSendAmount: (String) -> Unit,
    addPointsToTheUser: () -> Unit
){
    val noNumberPass = remember { mutableStateOf(false) }
    val usersNamesMenuExpanded = remember { mutableStateOf(false) }
    val menuIcon = remember { mutableStateOf(Icons.Default.KeyboardArrowDown) }

    Column (
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Column (modifier = Modifier.padding(20.dp)){
            OutlinedTextField(
                modifier = Modifier.padding(top = 15.dp, bottom = 5.dp),
                value = coinsToSend,
                label = { Text("Insert the money amount to share") },
                onValueChange = { cToSend ->
                    updatePointsToSendAmount(cToSend)
                }
            )
            if (noNumberPass.value) Text("You need to enter a number", color = Color.Red)
        }
        Row {
            Text("User name: ${userName}")
            Box{
                IconButton(
                    onClick = {
                        usersNamesMenuExpanded.value = !usersNamesMenuExpanded.value
                        if (!usersNamesMenuExpanded.value){
                            menuIcon.value = Icons.Default.KeyboardArrowUp
                        } else{
                            menuIcon.value = Icons.Default.KeyboardArrowDown
                        }
                    }
                ) {
                    Icon(menuIcon.value, contentDescription = "Display users names")
                }
                DropdownMenu(
                    expanded = usersNamesMenuExpanded.value,
                    onDismissRequest = { usersNamesMenuExpanded.value = false }
                ) {
                    for (uN in userNamesList){
                        DropdownMenuItem(
                            text = { Text("- $uN") },
                            onClick = { selectEmployeeByUserName(uN) }
                        )
                    }
                }
            }
        }
        Button(onClick = {
            if (coinsToSend == ""){
                noNumberPass.value = true
            } else{
                noNumberPass.value = false
                addPointsToTheUser()
            }
        }) {
            Text("Send Points")
        }
    }
}