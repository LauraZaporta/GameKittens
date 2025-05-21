package cat.itb.m78.exercices.EcoPetsProject.Others


import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import androidx.compose.ui.Alignment
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.material3.NavigationBar
import androidx.compose.ui.text.style.TextAlign
import cat.itb.m78.exercices.EcoPetsProject.DTOs.Task

data class NavigationBarItem(
    val text: String,
    val icon: ImageVector,
    val function: () -> Unit
)
data class NavigationBarItemNoText(
    val icon: ImageVector,
    val function: () -> Unit
)

@Composable
fun GenerateNavigationBarBottom(listNavElements : List<NavigationBarItem>)
{
    NavigationBar(containerColor = ColorConstants.colorAncientPink){
        listNavElements.forEach { item ->
            NavigationBarItem(
                selected = false,
                onClick = item.function,
                icon = { Icon(imageVector = item.icon,
                    contentDescription = null,
                    tint = Color.White)},
                label = {Text(item.text,
                    color = Color.White,
                    fontFamily = getFontFamily())}
            )
        }
    }
}

@Composable
fun GenerateNavigationBarTop(listNavElements : List<NavigationBarItemNoText>, points : Int)
{
    NavigationBar(containerColor = ColorConstants.colorAncientPink,
        modifier = Modifier.height(120.dp)) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 55.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "$$points",
                color = Color.White,
                fontSize = 5.em,
                fontFamily = getFontFamily(),
                modifier = Modifier.padding(start = 40.dp)
            )
            Spacer(Modifier.width(200.dp))
            listNavElements.forEach { item ->
                NavigationBarItem(
                    selected = false,
                    onClick = item.function,
                    icon = { Icon(imageVector = item.icon,
                        contentDescription = null,
                        tint = Color.White)},
                )
            }
            Spacer(Modifier.width(25.dp))
        }
    }
}

@Composable
fun GenerateImageButton(function : () -> Unit, text : String){
    Button(
        modifier = Modifier.height(40.dp).width(120.dp).padding(3.dp),
        onClick = function,
        shape = RoundedCornerShape(10.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = ColorConstants.colorCottonPink)
    ) {
        Text(text,
            color = ColorConstants.colorGrey,
            fontSize = 3.em,
            fontFamily = getFontFamily()
        )
    }
}