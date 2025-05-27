package cat.itb.m78.exercices.EcoPetsProject.Screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import androidx.lifecycle.viewmodel.compose.viewModel
import cat.itb.m78.exercices.EcoPetsProject.DTOs.Employee
import cat.itb.m78.exercices.EcoPetsProject.Others.ColorConstants
import cat.itb.m78.exercices.EcoPetsProject.Others.getFontFamily
import cat.itb.m78.exercices.EcoPetsProject.ViewModels.VMSendPoints

@Composable
fun ScreenSendPoints(){
    val viewModel = viewModel{ VMSendPoints() }

    ScreenSendPointsArguments(
        userName = viewModel.chosenUserName,
        userList = viewModel.employees.value,
        nameList = viewModel.employeesNames.value,
        pointsToSend = viewModel.pointsToSend,
        pointsShared = viewModel.pointsSharedSuccess.value,
        employeeSelected = viewModel.employeeSelected,
        addPointsToTheUser = { viewModel.addPointsToTheUser() }
    )
}

@Composable
fun ScreenSendPointsArguments(
    userName: MutableState<String>,
    userList: List<Employee>,
    nameList: List<String>,
    pointsToSend: MutableState<String>,
    pointsShared: Boolean?,
    employeeSelected: MutableState<Boolean>,
    addPointsToTheUser: () -> Unit
){
    val filteredEmployees = userList.filter { it.userName.contains(userName.value, ignoreCase = true) }

    Column (
        modifier = Modifier
            .fillMaxSize()
            .padding(15.dp)
            .background(ColorConstants.colorCottonPink, shape = RoundedCornerShape(20.dp)),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        if (pointsShared != true) {
            Text("Send points",
                color = ColorConstants.colorGrey,
                fontSize = 12.em,
                fontFamily = getFontFamily()
            )
            Spacer(Modifier.height(20.dp))
            OutlinedTextField(
                value = userName.value,
                onValueChange = {
                    userName.value = it
                    if (!nameList.contains(userName.value)) employeeSelected.value = false
                },
                label = { Text("User to send points") },
                modifier = Modifier.width(300.dp)
            )
            Spacer(Modifier.height(10.dp))
            OutlinedTextField(
                value = pointsToSend.value,
                onValueChange = { pointsToSend.value = it },
                modifier = Modifier.width(300.dp),
                label = { Text("Number of points", color = ColorConstants.colorGrey) },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Number
                ),
                singleLine = true
            )
            Spacer(Modifier.height(35.dp))
            Button(
                modifier = Modifier.height(60.dp).width(100.dp),
                onClick = addPointsToTheUser,
                shape = RoundedCornerShape(20.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = ColorConstants.colorVanilla)
            ) {
                Text("Add",
                    color = ColorConstants.colorAncientPink,
                    fontSize = 4.em,
                    fontFamily = getFontFamily()
                )
            }
            Spacer(Modifier.height(40.dp))
            LazyColumn{
                if (!employeeSelected.value && userName.value != "") {
                    items(filteredEmployees.chunked(2)) { rowEmployees ->
                        Row(
                            modifier = Modifier.width(400.dp),
                            horizontalArrangement = Arrangement.Center
                        ) {
                            rowEmployees.forEach { emp ->
                                Card(
                                    modifier = Modifier
                                        .height(50.dp)
                                        .padding(horizontal = 4.dp)
                                        .clickable(
                                            enabled = true,
                                            onClickLabel = "Clickable name",
                                            onClick = { userName.value = emp.userName
                                                employeeSelected.value = true}
                                        )
                                ) {
                                    Column(
                                        modifier = Modifier
                                            .height(50.dp)
                                            .background(ColorConstants.colorAncientPink)
                                            .padding(8.dp),
                                        verticalArrangement = Arrangement.Center,
                                        horizontalAlignment = Alignment.CenterHorizontally
                                    ) {
                                        Text(
                                            emp.userName,
                                            color = Color.White,
                                            textAlign = TextAlign.Center,
                                            fontSize = 5.em,
                                            fontFamily = getFontFamily()
                                        )
                                    }
                                }
                            }
                        }
                        Spacer(Modifier.height(10.dp))
                    }
                }
            }
            if (pointsShared == false){
                Spacer(Modifier.height(20.dp))
                Text("The values are not valid!",
                    color = ColorConstants.colorAncientPink,
                    fontSize = 4.em,
                    fontFamily = getFontFamily()
                )
            }
        } else {
            Icon(Icons.Default.Check,
                contentDescription = null,
                modifier = Modifier.size(175.dp))
            Spacer(Modifier.height(10.dp))
            Text("Points successfully sent!",
                color = ColorConstants.colorGrey,
                modifier = Modifier.padding(bottom = 50.dp),
                fontSize = 5.5.em,
                fontFamily = getFontFamily()
            )
        }
    }
}