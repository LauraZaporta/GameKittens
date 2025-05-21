package cat.itb.m78.exercices.EcoPetsProject.Screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Face
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import cat.itb.m78.exercices.EcoPetsProject.DTOs.UserProfile
import cat.itb.m78.exercices.EcoPetsProject.Others.ColorConstants
import cat.itb.m78.exercices.EcoPetsProject.ViewModels.ProfileViewModel
import kotlinx.serialization.Serializable


@Serializable
data object ScreenProfile

@Composable
fun ScreenProfile(){
    val viewModel = viewModel{ ProfileViewModel() }
    viewModel.getCurrentUser(/*current user Id*/)

    Column (
        modifier = Modifier.fillMaxSize().background(color = ColorConstants.colorVanilla),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        NavHost(navController = rememberNavController(), startDestination = ScreenProfile) {
            composable<ScreenProfile> {
                ScreenProfileArguments(viewModel.user.value!!)
            }
        }
    }
}

@Composable
fun ScreenProfileArguments(user: UserProfile){
    Column (
        modifier = Modifier
            .fillMaxSize()
            .padding(15.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(user.userName,
            fontSize = 35.sp,
            fontWeight = FontWeight(800),
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.size(25.dp))
        Card(
            modifier = Modifier.fillMaxWidth().padding(start = 40.dp, end = 40.dp),
            shape = RoundedCornerShape(16.dp)
        ) {
            Column (horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier
                        .background(color = ColorConstants.colorCottonPink)
                        .padding(20.dp)
            ) {
                Text(user.nameAndSurname,
                    fontSize = 20.sp,
                    fontWeight = FontWeight(500),
                    textAlign = TextAlign.Center
                )
                Spacer(modifier = Modifier.size(10.dp))
                Column {
                    Row {
                        Spacer(modifier = Modifier.padding(20.dp))
                        Icon(Icons.Default.Face, contentDescription = "phone")
                        Text(" | DNI: ${user.dni} ",
                            fontSize = 15.sp,
                            fontWeight = FontWeight(500),
                            textAlign = TextAlign.Start
                        )
                    }
                    Row {
                        Spacer(modifier = Modifier.padding(20.dp))
                        Icon(Icons.Default.Phone, contentDescription = "phone")
                        Text(" | Phone: ${user.phone}",
                            fontSize = 15.sp,
                            fontWeight = FontWeight(500),
                            textAlign = TextAlign.Start
                        )
                    }
                    Row {
                        Spacer(modifier = Modifier.padding(20.dp))
                        Icon(Icons.Default.Email, contentDescription = "phone")
                        Text(" | Email: ${user.email}",
                            fontSize = 15.sp,
                            fontWeight = FontWeight(500),
                            textAlign = TextAlign.Start
                        )
                    }
                }
            }
        }
        Spacer(modifier = Modifier.size(50.dp))
        if (user.petHunger.toInt() < 3){
            Text("My pet ${user.petName} is okay",
                fontSize = 20.sp,
                fontWeight = FontWeight(500),
                textAlign = TextAlign.Center
            )
        } else{
            if (user.petHunger.toInt() < 7){
                Row{
                    Text("My pet ${user.petName}",
                        fontSize = 20.sp,
                        fontWeight = FontWeight(500),
                        textAlign = TextAlign.Center
                    )
                    Text(" is hungry!",
                        color = Color.Yellow,
                        fontSize = 20.sp,
                        fontWeight = FontWeight(500),
                        textAlign = TextAlign.Center
                    )
                }
            } else{
                Row {
                    Text("My pet ${user.petName}",
                        fontSize = 20.sp,
                        fontWeight = FontWeight(500),
                        textAlign = TextAlign.Center
                    )
                    Text(" is next to die!!!",
                        color = Color.Red,
                        fontSize = 20.sp,
                        fontWeight = FontWeight(500),
                        textAlign = TextAlign.Center
                    )
                }
            }
        }
    }
}