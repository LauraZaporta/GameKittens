package cat.itb.m78.exercices.EcoPetsProject.DTOs

class Pet (
    val id: Int,
    val animal: Int, // one to five, each number it's an animal
    val name: String,
    val hunger: Int, // one to ten
    val prop: Int, // zero to five, each number biguer than zero it's a prov
    val petBeingPatUri: String,
    val petImageUri: String,
    val hungerUri: String,
    val aLotOfHungerUri: String,
    val employeeId: String
)