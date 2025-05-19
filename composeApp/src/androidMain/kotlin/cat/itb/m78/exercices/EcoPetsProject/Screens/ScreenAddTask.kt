package cat.itb.m78.exercices.EcoPetsProject.Screens

import android.content.Intent
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import cat.itb.m78.exercices.EcoPetsProject.DTOs.Task
import cat.itb.m78.exercices.EcoPetsProject.ViewModels.VMAddTask

@Composable
fun ScreenAddTask(imageUri: Uri?, navigateToScreenCamera: () -> Unit) {
    val addTaskVM = viewModel { VMAddTask() }
}

@Composable
fun ScreenAddTaskArguments(newUri: MutableState<Uri>)
{
    val context = LocalContext.current
    val galleryLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        if (uri != null) {
            try {
                context.contentResolver.takePersistableUriPermission(
                    uri,
                    Intent.FLAG_GRANT_READ_URI_PERMISSION
                )
            } catch (e: SecurityException) {
                e.printStackTrace()
            }
            newUri.value = uri
        }
    }
}