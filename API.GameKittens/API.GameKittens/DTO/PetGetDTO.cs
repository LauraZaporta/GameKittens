namespace API.GameKittens.DTO
{
    public class PetGetDTO
    {
        public int Id { get; set; }
        public int Animal { get; set; }
        public string Name { get; set; }
        public bool PetState { get; set; }
        public string NormalImage { get; set; }
        public string PetImage { get; set; }
        public string HungryImage { get; set; }
        public string TooHungryImage { get; set; }

        public int? AccessoryId { get; set; }
        public string UserId { get; set; }
    }
}