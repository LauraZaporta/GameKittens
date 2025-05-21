package cat.itb.m78.exercices.EcoPetsProject.Screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import cat.itb.m78.exercices.EcoPetsProject.Others.ColorConstants
import cat.itb.m78.exercices.EcoPetsProject.ViewModels.RankViewModel
import cat.itb.m78.exercices.EcoPetsProject.ViewModels.UserPoints
import kotlinx.serialization.Serializable

@Serializable
data object Rank

@Composable
fun ScreenRank(){
    val viewModel = viewModel{ RankViewModel() }
    viewModel.getRankList()

    Column (
        modifier = Modifier.fillMaxSize().background(ColorConstants.colorVanilla),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        NavHost(navController = rememberNavController(), startDestination = Rank) {
            composable<Rank> {
                ScreenRankArguments(ranking = viewModel.ranking)
            }
        }
    }
}

@Composable
fun ScreenRankArguments(
    ranking: List<String>
){
    Column (modifier = Modifier
        .fillMaxSize()
        .padding(20.dp)
        .background(ColorConstants.colorJamPink),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        LazyColumn (horizontalAlignment = Alignment.CenterHorizontally) {
            item{
                Text(ranking[0],
                    fontSize = 20.sp,
                    fontWeight = FontWeight(800),
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(15.dp)
                )
                Spacer(modifier = Modifier.size(20.dp))
            }
            for (i in 1..ranking.indices.last){
                item {
                    Text(ranking[i],
                        fontSize = 20.sp,
                        fontWeight = FontWeight(700),
                        textAlign = TextAlign.Center
                    )
                    Spacer(modifier = Modifier.size(15.dp))
                }
            }
        }
    }
}