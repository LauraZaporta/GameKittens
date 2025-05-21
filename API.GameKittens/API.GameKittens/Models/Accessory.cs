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
        public string ImageURL { get; set; }
        public int Price { get; set; }

        // Relación 1:N - Un accesorio puede estar en varias mascotas
        public List<Pet> Pets { get; set; } = new();
    }
}
