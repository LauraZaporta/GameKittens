package cat.itb.m78.exercices.EcoPetsProject.Screens

import android.content.Intent
import android.content.Context
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.layout.size
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.runtime.Composable
import androidx.compose.material3.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.IconButton
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import androidx.lifecycle.viewmodel.compose.viewModel
import cat.itb.m78.exercices.EcoPetsProject.Others.ColorConstants
import cat.itb.m78.exercices.EcoPetsProject.Others.GenerateImageButton
import cat.itb.m78.exercices.EcoPetsProject.Others.GenerateIndeterminateCircularIndicator
import cat.itb.m78.exercices.EcoPetsProject.Others.getFontFamily
import cat.itb.m78.exercices.EcoPetsProject.ViewModels.VMAddTask
import coil3.compose.AsyncImage

@Composable
fun ScreenAddTask(imageUri: Uri?, navigateToScreenCamera: () -> Unit) {
    val addTaskVM = viewModel { VMAddTask() }

    if (imageUri != null){
        addTaskVM.uri.value = imageUri
    }
    ScreenAddTaskArguments(addTaskVM.uri, addTaskVM.title, addTaskVM.desc, addTaskVM.addingImage,
        addTaskVM.validNewTask.value ,addTaskVM::addTask, navigateToScreenCamera,
        addTaskVM.insertLoaded.value)
}

@Composable
fun ScreenAddTaskArguments(newUri: MutableState<Uri?>, newTitle : MutableState<String>,
                           newDesc : MutableState<String>, addingImage: MutableState<Boolean>,
                           validTask: Boolean?, addTask : (Context) -> Unit,
                           navigateToScreenCamera: () -> Unit, insertLoaded: Boolean)
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

    Column(modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally)
    {
        Text(
            "Add task",
            fontSize = 7.em,
            fontFamily = getFontFamily(),
            color = ColorConstants.colorGrey
        )
        Spacer(Modifier.height(25.dp))
        OutlinedTextField(
            modifier = Modifier.width(300.dp),
            value = newTitle.value,
            onValueChange = { newTitle.value = it },
            label = { Text("Title", color = ColorConstants.colorGrey) },
            singleLine = true
        )
        Spacer(Modifier.height(15.dp))
        OutlinedTextField(
            modifier = Modifier.height(100.dp).width(300.dp),
            value = newDesc.value,
            onValueChange = { newDesc.value = it },
            label = { Text("Description", color = ColorConstants.colorGrey) },
            maxLines = 4
        )
        Spacer(Modifier.height(30.dp))
        if (newUri.value != null) {
            AsyncImage(
                model = newUri.value,
                contentDescription = "Captured image",
                modifier = Modifier
                    .width(200.dp)
                    .height(120.dp)
                    .clip(RoundedCornerShape(12.dp))
                    .border(BorderStroke(1.dp, ColorConstants.colorCottonPink), RoundedCornerShape(12.dp)),
                contentScale = ContentScale.Crop
            )
        } else {
            Column(
                modifier = Modifier
                    .border(
                        BorderStroke(1.dp, SolidColor(ColorConstants.colorJamPink)),
                        shape = RoundedCornerShape(7.dp)
                    )
                    .width(200.dp)
                    .height(120.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            )
            {
                IconButton(onClick = { addingImage.value = true })
                {
                    Icon(
                        imageVector = Icons.Filled.AddCircle, contentDescription = "Camera",
                        modifier = Modifier.size(100.dp),
                        tint = ColorConstants.colorCottonPink
                    )
                }
                Spacer(Modifier.height(5.dp))
                Text("Add image", fontSize = 3.5.em)
            }
            if (addingImage.value){
                Spacer(Modifier.height(10.dp))
                Row{
                    GenerateImageButton({ navigateToScreenCamera() }, "Photo")
                    GenerateImageButton({ addingImage.value = false }, "Cancel")
                }
            }
        }
        Spacer(Modifier.height(30.dp))
        Button(
            modifier = Modifier.height(60.dp).width(100.dp),
            onClick = { addTask(context) },
            shape = RoundedCornerShape(20.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = ColorConstants.colorCottonPink)
        ) {
            Text("Add",
                color = ColorConstants.colorGrey,
                fontSize = 4.em,
                fontFamily = getFontFamily()
            )
        }
        if (!insertLoaded){
            Spacer(Modifier.height(25.dp))
            GenerateIndeterminateCircularIndicator(ColorConstants.colorWhiteNotWhite, ColorConstants.colorAncientPink)
        }
        if (validTask == true){
            Spacer(Modifier.height(20.dp))
            Text("Task inserted!", color = ColorConstants.colorGreen)
        }
        else if (validTask == false){
            Spacer(Modifier.height(20.dp))
            Text("One of the fields is missing!", color = ColorConstants.colorRed)
        }
    }
}