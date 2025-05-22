package cat.itb.m78.exercices.EcoPetsProject.ViewModels

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.MutableState
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
            10,
            3,
            "https://www.models-resource.com/resources/big_icons/39/38901.png?updated=1587677584",
            "https://static.wikia.nocookie.net/youkaiwatch/images/2/2f/Jibanyan.png/revision/latest?cb=20230122075514&path-prefix=es",
            "https://static.wikia.nocookie.net/versus-connections/images/c/cb/Imgbin-yo-kai-watch-2-jibanyan-nintendo-3ds-nintendo-Lm9ha59tYnVq29vuADbVSAWS0.png/revision/latest?cb=20240211172618",
            "https://pbs.twimg.com/media/DUrQQ4-U0AAzZiS.png",
            ""
        )

        if(pet.value!!.hunger < 3){
            isPetHungry.value = false
        } else if (pet.value!!.hunger > 7){
            isPetHungry.value = true
        } else{
            isPetHungry.value = null
        }

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

        accessoryList.value = listOf(
            Prop(
                id = 3,
                name = "buts",
                imageUri = "https://www.equipassio.com/52888-thickbox_default/botas-de-equitacion-de-cuero-sintetico-portland-para-ninos-de-elt.jpg",
                price = 100,
                unlocked = true
            ),
            Prop(
                id = 2,
                name = "hat",
                imageUri = "",
                price = 10,
                unlocked = false
            )
        )

        for (prop in accessoryList.value!!){
            if (prop.id == pet.value!!.prop){
                petProp.value = prop.imageUri
            }
        }
    }

    fun updatePetHunger(valueToAdd: Int){

    }

    fun updateUserCoins(){

    }
}