package cat.itb.m78.exercices.EcoPetsProject.Screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import cat.itb.m78.exercices.EcoPetsProject.ViewModels.RankViewModel
import cat.itb.m78.exercices.EcoPetsProject.ViewModels.UserPoints
import kotlinx.serialization.Serializable

@Serializable
data object Rank

@Composable
fun ScreenRank(){
    val viewModel = viewModel{ RankViewModel() }
    viewModel.getRankList()

    Column (modifier = Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally) {
        NavHost(navController = rememberNavController(), startDestination = Rank) {
            composable<Rank> {
                ScreenRankArguments(usersPoints = viewModel.usersPoints)
            }
        }
    }
}

@Composable
fun ScreenRankArguments(
    usersPoints: List<UserPoints>
){
    val rankLine = remember { mutableStateOf("") }

    Column (horizontalAlignment = Alignment.CenterHorizontally) {
        LazyColumn {
            rankLine.value += usersPoints[0].userName
            for (i in 9..40) {
                rankLine.value += " "
            }
            rankLine.value += usersPoints[0].points
            item {
                Text(rankLine.value, fontSize = 20.sp, fontWeight = FontWeight(900))
                Spacer(modifier = Modifier.size(25.dp))
            }
            for (i in 1..usersPoints.indices.last){
                rankLine.value = ""
                for (j in usersPoints[i].userName.indices){

                }
            }

            // DON'T WORK HOW IT WAS SUPOSE TO DO
            for (up in usersPoints){
                rankLine.value = ""
                if (up.points.all { it.isDigit() }) {
                    for (i in up.userName.indices) {
                        rankLine.value += up.userName[i]
                    }
                    rankLine.value += " "
                    for (i in up.userName.length..40) {
                        rankLine.value += "."
                    }
                    rankLine.value += " "
                    for (i in up.points.indices) {
                        rankLine.value += up.points[i]
                    }
                    item {
                        Text(rankLine.value, fontSize = 20.sp, fontWeight = FontWeight(700))
                        Spacer(modifier = Modifier.size(15.dp))
                    }
                } else{
                    rankLine.value += up.userName
                    for (i in 9..40) {
                        rankLine.value += " "
                    }
                    rankLine.value += up.points
                    item {
                        Text(rankLine.value, fontSize = 20.sp, fontWeight = FontWeight(900))
                        Spacer(modifier = Modifier.size(25.dp))
                    }
                }
            }
        }
    }
}