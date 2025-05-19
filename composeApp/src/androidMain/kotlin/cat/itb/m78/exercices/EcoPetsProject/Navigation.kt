import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.MailOutline
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import cat.itb.m78.exercices.EcoPetsProject.Others.GenerateNavigationBarBottom
import cat.itb.m78.exercices.EcoPetsProject.Others.GenerateNavigationBarTop
import cat.itb.m78.exercices.EcoPetsProject.Others.NavigationBarItem
import cat.itb.m78.exercices.EcoPetsProject.Screens.ScreenDetailsTask
import cat.itb.m78.exercices.EcoPetsProject.Screens.ScreenListTasks
import kotlinx.serialization.Serializable

@Serializable
object Destination{
    @Serializable
    data object ScreenAddTask
    @Serializable
    data object ScreenCamera
    @Serializable
    data class ScreenDetailsTask (val idTask : Int)
    @Serializable
    data object ScreenListTasks
    @Serializable
    data object ScreenLogin
    @Serializable
    data object ScreenPet
    @Serializable
    data object ScreenProfile
    @Serializable
    data object ScreenRank
    @Serializable
    data object ScreenSendPoints
}

@Composable
fun Navigation(){
    val navController = rememberNavController()

    val listNavElementsBottom = listOf(
        NavigationBarItem("Tasks", Icons.Default.Check) { navController.navigate(Destination.ScreenListTasks) },
        NavigationBarItem("Pet", Icons.Default.FavoriteBorder) { navController.navigate(Destination.ScreenPet) },
        NavigationBarItem("Ranking", Icons.Default.Star) { navController.navigate(Destination.ScreenRank) }
    )
    val listNavElementsTop = listOf(
        NavigationBarItem("Send points", Icons.Default.MailOutline) { navController.navigate(Destination.ScreenSendPoints) },
        NavigationBarItem("Profile", Icons.Default.AccountCircle) { navController.navigate(Destination.ScreenProfile) }
    )

    Scaffold (topBar = { GenerateNavigationBarTop(listNavElementsTop, 0) },
        bottomBar = { GenerateNavigationBarBottom(listNavElementsBottom) })
    { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            NavHost(navController = navController, startDestination = Destination.ScreenPet) {
                composable<Destination.ScreenAddTask> { }
                composable<Destination.ScreenCamera> { }
                composable<Destination.ScreenDetailsTask> { backStack ->
                    val idTask = backStack.toRoute<Destination.ScreenDetailsTask>().idTask
                    ScreenDetailsTask(idTask)
                }
                composable<Destination.ScreenListTasks> {
                    ScreenListTasks(navigateToScreenAddTask = {navController.navigate(Destination.ScreenAddTask)},
                        navigateToScreenDetailsTask = {navController.navigate(Destination.ScreenDetailsTask(it))})
                }
                composable<Destination.ScreenLogin> { }
                composable<Destination.ScreenPet> { }
                composable<Destination.ScreenProfile> { }
                composable<Destination.ScreenRank> { }
                composable<Destination.ScreenSendPoints> { }
            }
        }
    }
}