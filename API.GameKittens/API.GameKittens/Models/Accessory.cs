using System.ComponentModel.DataAnnotations;
using System.ComponentModel.DataAnnotations.Schema;

namespace API.GameKittens.Models
{
    [Table("Accessories")]
    public class Accessory
    {
        [Key]
        [DatabaseGenerated(DatabaseGeneratedOption.Identity)]
        public int Id { get; set; }
        public string Name { get; set; }
        public string? ImageURL { get; set; }
        public int Price { get; set; }

        // Muchos pets pueden tener este accesorio equipado
        public List<Pet> EquippedByPets { get; set; } = new();

        // Muchos pets pueden tener este accesorio como disponible
        public List<Pet> AvailableInPets { get; set; } = new();
    }
}
