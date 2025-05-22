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
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.runtime.MutableState
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
import cat.itb.m78.exercices.EcoPetsProject.DTOs.Employee
import cat.itb.m78.exercices.EcoPetsProject.Others.ColorConstants
import cat.itb.m78.exercices.EcoPetsProject.Others.GenerateSearchableDropdown
import cat.itb.m78.exercices.EcoPetsProject.ViewModels.SendPointsViewModel

@Composable
fun ScreenSendPoints(){
    val viewModel = viewModel{ SendPointsViewModel() }

    ScreenSendPointsArguments(
        userName = viewModel.chosenUserName,
        userList = viewModel.employees.value,
        pointsToSend = viewModel.pointsToSendField.value,
        pointsShared = viewModel.pointsSharedSuccess.value,
        updatePointsToSendAmount = { viewModel.updatePointsToSendAmount() },
        addPointsToTheUser = { viewModel.addPointsToTheUser() }
    )
}

@Composable
fun ScreenSendPointsArguments(
    userName: MutableState<String>,
    userList: List<Employee>,
    pointsToSend: String,
    pointsShared: Boolean,
    updatePointsToSendAmount: (String) -> Unit,
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
        if (!pointsShared) {
            GenerateSearchableDropdown(userName, userList)
        }
    }
}