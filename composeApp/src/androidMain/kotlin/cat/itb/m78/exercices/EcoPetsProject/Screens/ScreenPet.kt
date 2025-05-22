package cat.itb.m78.exercices.EcoPetsProject.Screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import cat.itb.m78.exercices.EcoPetsProject.DTOs.Pet
import cat.itb.m78.exercices.EcoPetsProject.DTOs.Prop
import cat.itb.m78.exercices.EcoPetsProject.Others.NavigationBarItem
import cat.itb.m78.exercices.EcoPetsProject.ViewModels.PetViewModel
import coil3.compose.AsyncImage

@Composable
fun ScreenPet(){
    val viewModel = viewModel{ PetViewModel() }

    ScreenPetArguments(
        pet = viewModel.pet.value!!,
        accessoryList = viewModel.accessoryList.value!!,
        isPetHungry = viewModel.isPetHungry.value,
        updatePetHunger = { viewModel.updatePetHunger() }
    )
}

@Composable
fun ScreenPetArguments(
    pet: Pet,
    accessoryList: List<Prop>,
    isPetHungry: Boolean?,
    updatePetHunger: () -> Unit
){
    val showCloths = remember { mutableStateOf(false) }

    Row (modifier = Modifier.fillMaxWidth().padding(40.dp)){
        Column(modifier = Modifier.fillMaxHeight(), horizontalAlignment = Alignment.CenterHorizontally) {
            Text(pet.name,
                fontSize = 35.sp,
                fontWeight = FontWeight(800),
                textAlign = TextAlign.Center
            )
            Box {
                when (isPetHungry){
                    false -> {
                        AsyncImage(
                            model = pet.petImageUri,
                            contentDescription = "Pet",
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(200.dp)
                                .padding(bottom = 5.dp)
                                .clip(RoundedCornerShape(12.dp)),
                            contentScale = ContentScale.Crop
                        )
                    }
                    null -> {
                        AsyncImage(
                            model = pet.hungerUri,
                            contentDescription = "Pet hungry",
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(200.dp)
                                .padding(bottom = 5.dp)
                                .clip(RoundedCornerShape(12.dp)),
                            contentScale = ContentScale.Crop
                        )
                    }
                    true -> {
                        AsyncImage(
                            model = pet.aLotOfHungerUri,
                            contentDescription = "Pet so hungry",
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(200.dp)
                                .padding(bottom = 5.dp)
                                .clip(RoundedCornerShape(12.dp)),
                            contentScale = ContentScale.Crop
                        )
                    }
                }
            }
        }
        Spacer(modifier = Modifier.size(30.dp))
        Column(modifier = Modifier.fillMaxHeight(), horizontalAlignment = Alignment.End) {
            Box(
                contentAlignment = Alignment.TopEnd
            ) {
                IconButton(
                    onClick = {
                        showCloths.value = !showCloths.value
                    },
                    modifier = Modifier.size(50.dp)
                ) {
                    Icon(
                        Icons.Default.ShoppingCart,
                        contentDescription = "Pet Cloths",
                        modifier = Modifier.size(40.dp)
                    )
                }
            }
            Box {
                IconButton(
                    onClick = {
                        showCloths.value = !showCloths.value
                    },
                    modifier = Modifier.size(50.dp)
                ) {
                    Icon(
                        Icons.Default.ShoppingCart,
                        contentDescription = "Pet Cloths",
                        modifier = Modifier.size(40.dp)
                    )
                }
                NavigationBarItem(" - $10", Icons.Default.Add){
                    updatePetHunger()
                }
            }
        }
    }
}