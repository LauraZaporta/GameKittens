package cat.itb.m78.exercices.EcoPetsProject.Screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import cat.itb.m78.exercices.EcoPetsProject.DTOs.Pet
import cat.itb.m78.exercices.EcoPetsProject.DTOs.Prop
import cat.itb.m78.exercices.EcoPetsProject.Others.ColorConstants
import cat.itb.m78.exercices.EcoPetsProject.ViewModels.PetViewModel
import coil3.compose.AsyncImage

@Composable
fun ScreenPet(){
    val viewModel = viewModel{ PetViewModel() }

    viewModel.checkPetHunger()
    viewModel.checkPetBeingPat()

    ScreenPetArguments(
        pet = viewModel.pet.value!!,
        propsList = viewModel.accessoryList.value!!,
        petImage = viewModel.petImage.value,
        petProp = viewModel.petProp.value,
        updatePetHunger = { viewModel.updatePetHunger() },
        unlockNewProp = { viewModel.unlockNewProp(it) },
        updatePetAccessory = { viewModel.updatePetAccessory(it) },
        updateUserCoins = { viewModel.updateUserCoins(it) }
    )
}

@Composable
fun ScreenPetArguments(
    pet: Pet,
    propsList: List<Prop>,
    petImage: String,
    petProp: String,
    updatePetHunger: () -> Unit,
    unlockNewProp: (Int) -> Unit,
    updatePetAccessory: (Int) -> Unit,
    updateUserCoins: (Int) -> Unit
){
    val showCloths = remember { mutableStateOf(false) }

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Bottom
    ) {
        Text(pet.name,
            fontSize = 35.sp,
            fontWeight = FontWeight(800),
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.size(20.dp))
        Box {
            if (petImage != "") {
                AsyncImage(
                    model = petImage,
                    contentDescription = "Pet image",
                    modifier = Modifier.size(400.dp)
                )
            }
            if (petProp != "") {
                AsyncImage(
                    model = petProp,
                    contentDescription = "Pet accessory",
                    modifier = Modifier.size(400.dp)
                )
            }
        }

        Spacer(modifier = Modifier.size(40.dp))

        if (showCloths.value){
            Row (
                modifier = Modifier
                    .fillMaxWidth()
                    .height(90.dp)
                    .background(ColorConstants.colorVanilla),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.End
            ) {
                LazyRow {
                    for (p in propsList){
                        item {
                            Column (
                                modifier = Modifier.padding(10.dp),
                                horizontalAlignment = Alignment.CenterHorizontally
                            ){
                                IconButton(
                                    onClick = {
                                        if (p.unlocked.value){
                                            updatePetAccessory(p.id)
                                        } else {
                                            unlockNewProp(p.id)
                                            updateUserCoins(p.price)
                                        }
                                    },
                                    modifier = Modifier.size(50.dp)
                                ) {
                                    AsyncImage(
                                        model = p.imageUri,
                                        contentDescription = p.name,
                                        modifier = Modifier
                                            .padding(5.dp)
                                            .size(300.dp),
                                        contentScale = ContentScale.Crop
                                    )
                                }
                                Text(p.name, textAlign = TextAlign.Center)
                                if (!p.unlocked.value) {
                                    Text(" -${p.price} €", textAlign = TextAlign.Center)
                                }
                            }
                        }
                    }
                }

                Spacer(modifier = Modifier.size(20.dp))

                IconButton(
                    onClick = {
                        showCloths.value = !showCloths.value
                    },
                    modifier = Modifier.size(50.dp),
                ) {
                    Icon(
                        Icons.Default.ShoppingCart,
                        contentDescription = "Pet Cloths",
                        modifier = Modifier.size(40.dp)
                    )
                }
            }
        }
        else{
            Row (
                modifier = Modifier
                    .fillMaxWidth()
                    .height(90.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.End
            ){
                IconButton(
                    onClick = {
                        showCloths.value = !showCloths.value
                    },
                    modifier = Modifier.size(50.dp),
                ) {
                    Icon(
                        Icons.Default.ShoppingCart,
                        contentDescription = "Pet Cloths",
                        modifier = Modifier.size(40.dp)
                    )
                }
            }
        }

        Row (
            modifier = Modifier
                .fillMaxWidth()
                .height(90.dp)
                .background(ColorConstants.colorVanilla),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.End
        ){
            val hungerWidth = remember { mutableStateOf(0) }
            if (pet.hunger.value < 10) { hungerWidth.value = 350 - pet.hunger.value * 35 }
            else { hungerWidth.value = 20 }

            if (pet.hunger.value < 3) {
                Row(
                    modifier = Modifier
                        .background(ColorConstants.colorCottonPink)
                        .padding(horizontal = 20.dp)
                        .width(hungerWidth.value.dp)
                        .height(30.dp)
                ) {
                    Text("${pet.hunger.value}", textAlign = TextAlign.Center)
                }
            } else if (pet.hunger.value < 7) {
                Row(
                    modifier = Modifier
                        .background(ColorConstants.colorJamPink)
                        .padding(horizontal = 20.dp)
                        .width(hungerWidth.value.dp)
                        .height(30.dp)
                ) {
                    Text("${pet.hunger.value}", textAlign = TextAlign.Center)
                }
            } else {
                Row(
                    modifier = Modifier
                        .background(ColorConstants.colorRed)
                        .padding(horizontal = 20.dp)
                        .width(hungerWidth.value.dp)
                        .height(30.dp)
                ) {
                    Text("${pet.hunger.value}", textAlign = TextAlign.Center)
                }
            }

            Column {
                IconButton(
                    onClick = {
                        updatePetHunger()
                    },
                    modifier = Modifier.size(50.dp)
                ) {
                    Icon(
                        Icons.Default.Info,
                        contentDescription = "Pet Food",
                        modifier = Modifier.size(40.dp)
                    )
                }
                Text(" -10 €")
            }
        }
    }
}