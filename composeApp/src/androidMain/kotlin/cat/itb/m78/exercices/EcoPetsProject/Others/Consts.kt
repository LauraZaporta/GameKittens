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
    val colorSoftAqua = Color(0XFF79C4CB)
    val colorDustyTeal = Color(0XFF7BA3A7)
    val colorElectricSky = Color(0XFF6BE6F0)
    val colorSlateMist = Color(0XFF738182)
    val colorDeepStone = Color(0XFF495C5E)
    val colorMidnightForest = Color(0XFF25383A)
    val colorOceanAbyss = Color(0XFF193133)
}