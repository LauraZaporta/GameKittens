namespace API.GameKittens.DTO
{
    public class PetInsertDTO
    {
        public int Animal { get; set; }
        public string Name { get; set; }
        public bool PetState { get; set; }
        public string IdleImage { get; set; }
        public string PetImage { get; set; }
        public string HungryImage { get; set; }
        public string ToHungryImage { get; set; }

        public int? AccessoryId { get; set; }
        public string UserId { get; set; }
    }
}
