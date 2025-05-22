package cat.itb.m78.exercices.EcoPetsProject.Screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import cat.itb.m78.exercices.EcoPetsProject.Others.ColorConstants
import cat.itb.m78.exercices.EcoPetsProject.ViewModels.SendPointsViewModel

@Composable
fun ScreenSendPoints(){
    val viewModel = viewModel{ SendPointsViewModel() }

    ScreenSendPointsArguments(
        userName = viewModel.userName.value,
        userNamesList = viewModel.namesList,
        coinsToSend = viewModel.coinsToSend.value,
        pointsShered = viewModel.pointsSharedSuccess.value,
        selectEmployeeByUserName = { viewModel.selectEmployeeByUserName(it) },
        updatePointsToSendAmount = { viewModel.updatePointsToSendAmount(it) },
        addPointsToTheUser = { viewModel.addPointsToTheUser() },
        acceptYouSharedThePoints = { viewModel.acceptYouSharedThePoints() }
    )
}

@Composable
fun ScreenSendPointsArguments(
    userName: String,
    userNamesList: List<String>,
    coinsToSend: String,
    pointsShered: Boolean,
    selectEmployeeByUserName: (String) -> Unit,
    updatePointsToSendAmount: (String) -> Unit,
    addPointsToTheUser: () -> Unit,
    acceptYouSharedThePoints: () -> Unit
){
    val noNumberPass = remember { mutableStateOf(false) }
    val noUserNamePass = remember { mutableStateOf(false) }
    val usersNamesMenuExpanded = remember { mutableStateOf(false) }
    val menuIcon = remember { mutableStateOf(Icons.Default.KeyboardArrowDown) }

    Column (
        modifier = Modifier.fillMaxSize().padding(15.dp).background(ColorConstants.colorJamPink),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        if (!pointsShered) {
            Column(modifier = Modifier.padding(15.dp).background(ColorConstants.colorCottonPink)) {
                OutlinedTextField(
                    modifier = Modifier.padding(15.dp),
                    value = coinsToSend,
                    label = { Text("Insert the money amount to share") },
                    onValueChange = { cToSend ->
                        updatePointsToSendAmount(cToSend)
                    }
                )
                if (noNumberPass.value) Text("You need to enter a number", color = Color.Red)
            }
            Column (modifier = Modifier.background(ColorConstants.colorCottonPink)) {
                Row (modifier = Modifier.padding(10.dp)){
                    Text("User name:")
                    Box {
                        IconButton(
                            onClick = {
                                usersNamesMenuExpanded.value = !usersNamesMenuExpanded.value
                                if (!usersNamesMenuExpanded.value) {
                                    menuIcon.value = Icons.Default.KeyboardArrowUp
                                } else {
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
                            for (i in 1..userNamesList.indices.last) {
                                DropdownMenuItem(
                                    text = { Text("- ${userNamesList[i]}") },
                                    onClick = {
                                        selectEmployeeByUserName(userNamesList[i])
                                        usersNamesMenuExpanded.value = !usersNamesMenuExpanded.value
                                    }
                                )
                            }
                        }
                    }
                }
                Text(userName, fontSize = 15.sp, modifier = Modifier.padding(10.dp), textAlign = TextAlign.Center)
                if (noUserNamePass.value) Text("You need to select a user", color = Color.Red)
            }
            Spacer(modifier = Modifier.size(20.dp))
            Button(
                onClick = {
                    if (coinsToSend == "") {
                        noNumberPass.value = true
                    } else {
                        noNumberPass.value = false
                    }

                    if (userName == ""){
                        noUserNamePass.value = true
                    } else{
                        noUserNamePass.value = false
                    }

                    if (coinsToSend != "" && userName != "") {
                        addPointsToTheUser()
                    }
                },
                colors = ButtonColors(
                    containerColor = ColorConstants.colorVanilla,
                    contentColor = Color.Black,
                    disabledContainerColor = ColorConstants.colorWhiteNotWhite,
                    disabledContentColor = Color.Black
                )
            ) {
                Text("Send Points")
            }
        }
        else {
            Spacer(modifier = Modifier.size(30.dp))
            Column (
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.background(ColorConstants.colorCottonPink)
            ) {
                Text("The points had been shared successfully",
                    modifier = Modifier.padding(20.dp)
                )
                Button(
                    onClick = { acceptYouSharedThePoints() },
                    modifier = Modifier.padding(bottom = 20.dp),
                    colors = ButtonColors(
                        containerColor = ColorConstants.colorVanilla,
                        contentColor = Color.Black,
                        disabledContainerColor = ColorConstants.colorWhiteNotWhite,
                        disabledContentColor = Color.Black
                    )
                ) {
                    Text("Continue")
                }
            }
        }
    }
}