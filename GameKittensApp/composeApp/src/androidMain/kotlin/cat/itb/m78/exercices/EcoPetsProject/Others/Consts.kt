package cat.itb.m78.exercices.EcoPetsProject.Others

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import m78exercices.composeapp.generated.resources.Res
import m78exercices.composeapp.generated.resources.Gabarito_VariableFont_wght

@Composable
fun getFontFamily() : FontFamily{
    return FontFamily(org.jetbrains.compose.resources.Font(Res.font.Gabarito_VariableFont_wght))
}

object ColorConstants {
    val colorAncientPink = Color(0XFFB7828E)
    val colorJamPink = Color(0XFFE5A3B1 )
    val colorCottonPink = Color(0XFFFBCBD7)
    val colorVanilla = Color(0XFFFDF8D3)
    val colorWhiteNotWhite = Color(0XFFFFFCE5 )
    val colorGrey = Color(0XFF333333)
    val colorGreen = Color(0XFF74C47C)
    val colorRed = Color(0XFFCF5753)
}

val brush = Brush.linearGradient(listOf(ColorConstants.colorCottonPink,
    Color.White,
    ColorConstants.colorCottonPink))

const val apiBaseUrl = "https://apigamekittens2-c6e0h2cwcecxhsak.canadacentral-01.azurewebsites.net/api"
