package cat.itb.m78.exercices.EcoPetsProject.Screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import cat.itb.m78.exercices.EcoPetsProject.API.APIUsers
import cat.itb.m78.exercices.EcoPetsProject.API.UserData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class VMTEST: ViewModel(){
    val userList = mutableStateOf<List<UserData>>(emptyList())

    init {
        viewModelScope.launch(Dispatchers.Default) {
            userList.value = APIUsers().listUsers()
        }
    }
}

@Composable
fun APITEST() {
    val viewModel = viewModel{ VMTEST() }

    if (viewModel.userList.value.isNotEmpty()){
        LazyColumn (horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier.background(Color.White).fillMaxSize()){
            items(viewModel.userList.value) { user ->
                Text(user.name)
                Text(user.surname)
            }
        }
    }
}