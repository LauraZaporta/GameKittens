package cat.itb.m78.exercices.EcoPetsProject.Screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.ThumbUp
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import androidx.lifecycle.viewmodel.compose.viewModel
import cat.itb.m78.exercices.EcoPetsProject.DTOs.Employee
import cat.itb.m78.exercices.EcoPetsProject.Others.ColorConstants
import cat.itb.m78.exercices.EcoPetsProject.Others.GenerateSearchableDropdown
import cat.itb.m78.exercices.EcoPetsProject.Others.getFontFamily
import cat.itb.m78.exercices.EcoPetsProject.ViewModels.VMSendPoints

@Composable
fun ScreenSendPoints(){
    val viewModel = viewModel{ VMSendPoints() }

    ScreenSendPointsArguments(
        userName = viewModel.chosenUserName,
        userList = viewModel.employees.value,
        pointsToSend = viewModel.pointsToSend,
        pointsShared = viewModel.pointsSharedSuccess.value,
        addPointsToTheUser = { viewModel.addPointsToTheUser() }
    )
}

@Composable
fun ScreenSendPointsArguments(
    userName: MutableState<String>,
    userList: List<Employee>,
    pointsToSend: MutableState<String>,
    pointsShared: Boolean?,
    addPointsToTheUser: () -> Unit
){
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
            GenerateSearchableDropdown(userName, userList)
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