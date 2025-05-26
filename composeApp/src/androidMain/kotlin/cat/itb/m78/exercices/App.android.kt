package cat.itb.m78.exercices

import Navigation
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import cat.itb.m78.exercices.EcoPetsProject.Screens.InitialNavigation

class AppActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent { InitialNavigation() }
    }
}

@Preview
@Composable
fun AppPreview() { InitialNavigation() }