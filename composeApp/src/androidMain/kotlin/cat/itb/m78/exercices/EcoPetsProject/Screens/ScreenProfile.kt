package cat.itb.m78.exercices.EcoPetsProject.Screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import cat.itb.m78.exercices.EcoPetsProject.DTOs.UserProfile
import cat.itb.m78.exercices.EcoPetsProject.ViewModels.ProfileViewModel
import kotlinx.serialization.Serializable


@Serializable
data object ScreenProfile

@Composable
fun ScreenProfile(){
    val viewModel = viewModel{ ProfileViewModel() }
    viewModel.getCurrentUser(/*current user Id*/)

    Column (modifier = Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally) {
        NavHost(navController = rememberNavController(), startDestination = ScreenProfile) {
            composable<ScreenProfile> {
                ScreenProfileArguments(viewModel.user.value!!)
            }
        }
    }
}

@Composable
fun ScreenProfileArguments(user: UserProfile){
    Column {
        Text(user.userName, fontSize = 30.sp)
        Spacer(modifier = Modifier.size(20.dp))
        Text(user.nameAndSurname, fontSize = 15.sp)
        Spacer(modifier = Modifier.size(10.dp))
        Text("DNI: ${user.dni} \nPhone: ${user.phone} \nEmail: ${user.email}")
        Spacer(modifier = Modifier.size(30.dp))
        if (user.petHunger.toInt() < 3){
            Text("My pet ${user.petName} is okay")
        } else{
            if (user.petHunger.toInt() < 7){
                Row{
                    Text("My pet ${user.petName}")
                    Text(" is hungry!", color = Color.Yellow)
                }
            } else{
                Text("My pet ${user.petName}")
                Text(" is next to die!!!", color = Color.Red)
            }
        }
    }
}