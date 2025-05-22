package cat.itb.m78.exercices.EcoPetsProject.Screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Face
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import cat.itb.m78.exercices.EcoPetsProject.DTOs.UserProfile
import cat.itb.m78.exercices.EcoPetsProject.Others.ColorConstants
import cat.itb.m78.exercices.EcoPetsProject.Others.GenerateRowEmployeeInfo
import cat.itb.m78.exercices.EcoPetsProject.Others.GenerateRowPetInfo
import cat.itb.m78.exercices.EcoPetsProject.Others.getFontFamily
import cat.itb.m78.exercices.EcoPetsProject.ViewModels.VMProfile
import cat.itb.m78.exercices.EcoPetsProject.settings
import com.russhwolf.settings.get

@Composable
fun ScreenProfile(){
    val viewModel = viewModel{ VMProfile(settings["key"]) }

    if (viewModel.user.value != null){
        ScreenProfileArguments(viewModel.user.value!!, viewModel.isPetHungry.value)
    }
}

@Composable
fun ScreenProfileArguments(user: UserProfile, isPetHungry: Boolean?){
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(15.dp)
            .background(color = ColorConstants.colorAncientPink, shape = RoundedCornerShape(20.dp)),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(user.userName,
            fontSize = 10.em,
            fontFamily = getFontFamily(),
            color = Color.White
        )
        Card(
            modifier = Modifier.fillMaxWidth().height(280.dp).padding(40.dp),
            shape = RoundedCornerShape(16.dp)
        ) {
            Column(modifier = Modifier
                .background(color = ColorConstants.colorCottonPink)
                .fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                GenerateRowEmployeeInfo("Name: ${user.nameAndSurname}", Icons.Default.Person)
                Spacer(Modifier.height(10.dp))
                GenerateRowEmployeeInfo("DNI: ${user.dni}", Icons.Default.Face)
                Spacer(Modifier.height(10.dp))
                GenerateRowEmployeeInfo("Phone: ${user.phone}", Icons.Default.Phone)
                Spacer(Modifier.height(10.dp))
                GenerateRowEmployeeInfo("Email: ${user.email}", Icons.Default.Email)
            }
        }
        when (isPetHungry) {
            false -> {
                GenerateRowPetInfo(user.petName, "is thriving!", ColorConstants.colorGreen)
            }
            null -> {
                GenerateRowPetInfo(user.petName, "is hungry!", ColorConstants.colorVanilla)
            }
            true -> {
                GenerateRowPetInfo(user.petName, "is next to die!", Color.Black)
            }
        }
    }
}