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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import cat.itb.m78.exercices.EcoPetsProject.DTOs.UserRank
import cat.itb.m78.exercices.EcoPetsProject.Others.ColorConstants
import cat.itb.m78.exercices.EcoPetsProject.Others.GenerateRankCard
import cat.itb.m78.exercices.EcoPetsProject.Others.brush
import cat.itb.m78.exercices.EcoPetsProject.Others.getFontFamily
import cat.itb.m78.exercices.EcoPetsProject.ViewModels.VMRank
import kotlinx.serialization.Serializable

@Composable
fun ScreenRank(){
    val viewModel = viewModel{ VMRank() }

    ScreenRankArguments(ranking = viewModel.topUsers.value)
}

@Composable
fun ScreenRankArguments(
    ranking: List<UserRank>
){
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(15.dp)
            .background(color = ColorConstants.colorCottonPink, shape = RoundedCornerShape(20.dp)),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top)
    {
        Text("Ranking",
            modifier = Modifier.padding(top = 40.dp),
            color = ColorConstants.colorGrey,
            fontSize = 14.em,
            fontFamily = getFontFamily()
        )
        Spacer(Modifier.height(15.dp))
        LazyColumn(modifier = Modifier.padding(15.dp)) {
            items(ranking) { user ->
                GenerateRankCard("${ranking.indexOf(user)+1}    ${user.userName}", user.points.toString()+" S")
            }
        }
    }
}