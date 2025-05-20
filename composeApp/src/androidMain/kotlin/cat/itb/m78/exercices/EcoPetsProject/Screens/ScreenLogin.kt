package cat.itb.m78.exercices.EcoPetsProject.Screens

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import cat.itb.m78.exercices.EcoPetsProject.ViewModels.LoginViewModel

@Composable
fun ScreenLogin(){
    val viewModel = viewModel{ LoginViewModel() }

}

@Composable
fun ScreenLoginArguments(){

}