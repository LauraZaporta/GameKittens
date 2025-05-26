package cat.itb.m78.exercices.EcoPetsProject.Screens

import Navigation
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.MailOutline
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.ui.Modifier
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import cat.itb.m78.exercices.EcoPetsProject.Others.ColorConstants
import cat.itb.m78.exercices.EcoPetsProject.Others.GenerateLoginField
import cat.itb.m78.exercices.EcoPetsProject.Others.getFontFamily
import cat.itb.m78.exercices.EcoPetsProject.ViewModels.VMLogin
import coil3.compose.AsyncImage
import kotlinx.serialization.Serializable

@Serializable
object Destination{
    @Serializable
    data object NavigationApp
    @Serializable
    data object ScreenLogin
}

@Composable
fun InitialNavigation(){
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = Destination.ScreenLogin) {
        composable<Destination.ScreenLogin> {
            ScreenLogin { navController.navigate(Destination.NavigationApp) }
            }
        composable<Destination.NavigationApp> {
            APITEST()
        }
    }
}

@Composable
fun ScreenLogin(navigateToNavigationApp: () -> Unit){
    val viewModel = viewModel{ VMLogin() }

    ScreenLoginArguments(
        email = viewModel.email,
        password = viewModel.password,
        passwordShown = viewModel.passwordShown.value,
        loginMessage = viewModel.loginMessage.value,
        login = { viewModel.login(navigateToNavigationApp) },
        changePasswordVisibility = viewModel::changePasswordVisibility,
    )
}

@Composable
fun ScreenLoginArguments(
    email: MutableState<String>,
    password: MutableState<String>,
    passwordShown: Boolean,
    loginMessage: String?,
    login: () -> Unit,
    changePasswordVisibility:() -> Unit,
){
    Column(modifier = Modifier.fillMaxSize()
        .background(ColorConstants.colorWhiteNotWhite),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally)
    {
        Text("EcoPets",
            color = ColorConstants.colorGrey,
            fontSize = 17.em,
            fontFamily = getFontFamily()
        )
        Spacer(Modifier.height(35.dp))
        AsyncImage(
            model = "https://media-public.canva.com/cRaKc/MAF0vtcRaKc/1/tl.png",
            contentDescription = "Cat pets logo",
        )
        Spacer(Modifier.height(40.dp))
        Column(verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.Start,
            modifier = Modifier.padding(start = 15.dp))
        {
            GenerateLoginField(email.value, { email.value = it }, false, {}, Icons.Default.MailOutline, "Email")
            Spacer(Modifier.height(10.dp))
            GenerateLoginField(
                actualText = password.value,
                onValueChangeText = { password.value = it },
                isPasswordHidden = !passwordShown,
                onToggleVisibility = changePasswordVisibility,
                icon = Icons.Default.Info,
                fieldName = "Password"
            )

        }
        Spacer(Modifier.height(40.dp))
        Button(
            modifier = Modifier.height(60.dp).width(100.dp),
            onClick = login,
            shape = RoundedCornerShape(20.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = ColorConstants.colorCottonPink)
        ) {
            Text("Login",
                color = ColorConstants.colorGrey,
                fontSize = 4.em,
                fontFamily = getFontFamily()
            )
        }
        Spacer(Modifier.height(30.dp))
        if (loginMessage != null){
            Text(loginMessage, color = ColorConstants.colorGrey)
        }
    }
}