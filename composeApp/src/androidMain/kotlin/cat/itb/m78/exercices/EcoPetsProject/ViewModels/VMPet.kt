package cat.itb.m78.exercices.EcoPetsProject.ViewModels

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import cat.itb.m78.exercices.EcoPetsProject.DTOs.Pet
import cat.itb.m78.exercices.EcoPetsProject.DTOs.Prop
import coil3.compose.AsyncImage

class PetViewModel : ViewModel() {
    val pet: MutableState<Pet?> = mutableStateOf(null)
    val accessoryList: MutableState<List<Prop>?> = mutableStateOf(null)
    val isPetHungry = mutableStateOf<Boolean?>(null)
    val petImage = mutableStateOf("")
    val petProp = mutableStateOf("")

    init {
        pet.value = Pet(
            1,
            1,
            "Jibanyan",
            false,
            hunger = mutableIntStateOf(10),
            prop = mutableIntStateOf(3),
            "https://www.models-resource.com/resources/big_icons/39/38901.png?updated=1587677584",
            "https://static.wikia.nocookie.net/youkaiwatch/images/2/2f/Jibanyan.png/revision/latest?cb=20230122075514&path-prefix=es",
            "https://static.wikia.nocookie.net/versus-connections/images/c/cb/Imgbin-yo-kai-watch-2-jibanyan-nintendo-3ds-nintendo-Lm9ha59tYnVq29vuADbVSAWS0.png/revision/latest?cb=20240211172618",
            "https://pbs.twimg.com/media/DUrQQ4-U0AAzZiS.png",
            ""
        )

        checkPetHunger()

        checkPetBeingPat()

        accessoryList.value = listOf(
            Prop(
                id = 3,
                name = "buts",
                imageUri = "https://www.equipassio.com/52888-thickbox_default/botas-de-equitacion-de-cuero-sintetico-portland-para-ninos-de-elt.jpg",
                price = 100,
                unlocked = mutableStateOf(true)
            ),
            Prop(
                id = 2,
                name = "hat",
                imageUri = "https://cdn.sanity.io/images/v8kybopt/production/4223fb615063597bc15a4f24ed9b65caff0fa32d-2000x2500.png?w=690&fit=max&auto=format",
                price = 10,
                unlocked = mutableStateOf(false)
            )
        )

        checkPetAccessory()
    }

    fun checkPetHunger(){
        if(pet.value!!.hunger.value < 3){
            isPetHungry.value = false
        } else if (pet.value!!.hunger.value > 7){
            isPetHungry.value = true
        } else{
            isPetHungry.value = null
        }
    }

    fun checkPetBeingPat(){
        if (pet.value!!.beingPat){
            petImage.value = pet.value!!.petBeingPatUri
        } else {
            when (isPetHungry.value){
                false -> {
                    petImage.value = pet.value!!.petImageUri
                }
                null -> {
                    petImage.value = pet.value!!.hungerUri
                }
                true -> {
                    petImage.value = pet.value!!.aLotOfHungerUri
                }
            }
        }
    }

    fun checkPetAccessory(){
        for (prop in accessoryList.value!!){
            if (prop.id == pet.value!!.prop.value){
                petProp.value = prop.imageUri
            }
        }
    }

    fun updatePetHunger(){
        if (pet.value!!.hunger.value > 1) {
            updateUserCoins(-10)
            pet.value!!.hunger.value -= 1
        }
    }

    fun unlockNewProp(propId: Int){
        for (prop in accessoryList.value!!){
            if (!prop.unlocked.value && prop.id == propId){
                prop.unlocked.value = true
            }
        }
    }

    fun updatePetAccessory(newProp: Int){
        pet.value!!.prop.value = newProp
        checkPetAccessory()
    }

    fun updateUserCoins(valueToAdd: Int){

    }
}