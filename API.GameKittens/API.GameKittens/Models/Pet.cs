using System.ComponentModel.DataAnnotations.Schema;
using System.ComponentModel.DataAnnotations;

namespace API.GameKittens.Models
{
    [Table("Pets")]
    public class Pet
    {
        [Key]
        [DatabaseGenerated(DatabaseGeneratedOption.Identity)]
        public int Id { get; set; }
        public int Animal { get; set; }
        public string Name { get; set; }
        public bool PetState { get; set; }
        public int Hunger { get; set; }
        public string IdleImage { get; set; }
        public string PetImage { get; set; }
        public string HungryImage { get; set; }
        public string ToHungryImage { get; set; }

        // FK from accessory
        public Accessory Accessory { get; set; }
        public List<Accessory> AvailablesAccessories { get; set; }
        // FK from user
        public ApplicationUser User { get; set; }
    }
}
