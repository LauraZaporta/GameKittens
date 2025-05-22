package cat.itb.m78.exercices.EcoPetsProject.ViewModels

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import cat.itb.m78.exercices.EcoPetsProject.DTOs.Pet
import cat.itb.m78.exercices.EcoPetsProject.DTOs.Prop

class PetViewModel : ViewModel() {
    val pet: MutableState<Pet?> = mutableStateOf(null)
    val accessoryList: MutableState<List<Prop>?> = mutableStateOf(null)
    val isPetHungry = mutableStateOf<Boolean?>(null)

    init {
        pet.value = Pet(
            1,
            1,
            "",
            4,
            0,
            "https://www.models-resource.com/resources/big_icons/39/38901.png?updated=1587677584",
            "https://static.wikia.nocookie.net/youkaiwatch/images/2/2f/Jibanyan.png/revision/latest?cb=20230122075514&path-prefix=es",
            "",
            "",
            ""
        )

        if(pet.value!!.hunger < 3){
            isPetHungry.value = false
        } else if (pet.value!!.hunger > 7){
            isPetHungry.value = true
        } else{
            isPetHungry.value = null
        }
    }

    fun updatePetHunger(){

    }
}