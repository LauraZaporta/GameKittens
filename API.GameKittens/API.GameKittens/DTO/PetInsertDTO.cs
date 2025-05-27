namespace API.GameKittens.DTO
{
    public class PetInsertDTO
    {
        public int Animal { get; set; }
        public string Name { get; set; }
        public bool PetState { get; set; }
        public IFormFile NormalImage { get; set; }
        public IFormFile PetImage { get; set; }
        public IFormFile HungryImage { get; set; }
        public IFormFile TooHungryImage { get; set; }
        public int? AccessoryId { get; set; }
        public string UserId { get; set; }
    }
}