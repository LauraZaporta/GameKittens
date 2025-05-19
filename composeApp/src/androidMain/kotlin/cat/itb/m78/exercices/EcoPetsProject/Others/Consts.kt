package cat.itb.m78.exercices.EcoPetsProject.Others

import androidx.compose.runtime.Composable
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
    val colorJamPink = Color(0XFF7BA3A7)
    val colorCottonPink = Color(0XFF6BE6F0)
    val colorVanilla = Color(0XFFFDF8D3)
    val colorWhiteNotWhite = Color(0XFFFFFCE5 )
    val colorGrey = Color(0XFF333333)
}