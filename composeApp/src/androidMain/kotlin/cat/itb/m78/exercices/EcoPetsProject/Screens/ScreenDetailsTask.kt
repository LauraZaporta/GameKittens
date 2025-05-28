package cat.itb.m78.exercices.EcoPetsProject.Screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Card
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import androidx.core.net.toUri
import androidx.lifecycle.viewmodel.compose.viewModel
import cat.itb.m78.exercices.EcoPetsProject.DTOs.Task
import cat.itb.m78.exercices.EcoPetsProject.Others.ColorConstants
import cat.itb.m78.exercices.EcoPetsProject.Others.GenerateIconButton
import cat.itb.m78.exercices.EcoPetsProject.Others.GenerateIndeterminateCircularIndicator
import cat.itb.m78.exercices.EcoPetsProject.Others.getFontFamily
import cat.itb.m78.exercices.EcoPetsProject.ViewModels.VMDetailsTask
import coil3.compose.AsyncImage

@Composable
fun ScreenDetailsTask(idTask : Int){
    val viewModel = viewModel{ VMDetailsTask(idTask) }

    if (viewModel.loading.value){
        Column(modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center)
        {
            GenerateIndeterminateCircularIndicator(ColorConstants.colorWhiteNotWhite,
                ColorConstants.colorAncientPink)
        }
    }
    else if (viewModel.task.value != null){
        ScreenDetailsTaskArguments( task = viewModel.task.value!!,
            like = viewModel::like,
            dislike = viewModel::dislike)
    }
    else {
        Column(modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center)
        {
            Text("No task found",
                color = ColorConstants.colorGrey,
                fontSize = 5.em,
                fontFamily = getFontFamily()
            )
        }
    }
}

@Composable
fun ScreenDetailsTaskArguments(
    task: Task,
    like:() -> Unit,
    dislike:() -> Unit
){
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(ColorConstants.colorWhiteNotWhite),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Card(
            modifier = Modifier.fillMaxWidth().padding(start = 40.dp, end = 40.dp),
            shape = RoundedCornerShape(16.dp)
        ) {
            Column(
                modifier = Modifier
                    .background(ColorConstants.colorAncientPink)
                    .padding(40.dp),
                verticalArrangement = Arrangement.spacedBy(20.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                AsyncImage(
                    model = task.imageURI,
                    contentDescription = "Bar Image",
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp)
                        .padding(bottom = 5.dp)
                        .clip(RoundedCornerShape(12.dp)),
                    contentScale = ContentScale.Crop
                )
                Text(
                    text = task.title,
                    fontFamily = getFontFamily(),
                    fontSize = 6.em,
                    color = Color.White
                )
                Text(
                    text = "Votes: ${task.votes}",
                    fontFamily = getFontFamily(),
                    fontSize = 4.em,
                    color = Color.White
                )
                Text(
                    text = "Description: ${task.description}",
                    fontFamily = getFontFamily(),
                    fontSize = 4.em,
                    textAlign = TextAlign.Center,
                    color = Color.White,
                    modifier = Modifier.fillMaxWidth()
                )
                Text(
                    text = "Made by ${task.employee.userName}",
                    fontFamily = getFontFamily(),
                    fontSize = 4.em,
                    color = Color.White
                )
            }
        }
        Row(modifier = Modifier.width(200.dp).padding(top = 20.dp),
            horizontalArrangement = Arrangement.SpaceAround){
            GenerateIconButton(like, ColorConstants.colorGreen, Icons.Default.Check, 70, 0)
            GenerateIconButton(dislike, ColorConstants.colorRed, Icons.Default.Close, 70, 0)
        }
    }
}