using System.ComponentModel.DataAnnotations;
using System.ComponentModel.DataAnnotations.Schema;

namespace API.GameKittens.Models
{
    [Table("STasks")]
    public class STask
    {
        [Key]
        [DatabaseGenerated(DatabaseGeneratedOption.Identity)]
        public int Id { get; set; }
        public int ValidationVotes { get; set; }
        public bool? Validate { get; set; } = null;
        public string Title { get; set; }
        public string? Description { get; set; }
        public string? ImageURL { get; set; }

        // FK to ApplicationUser
        public string UserId { get; set; }
        [ForeignKey("UserId")]
        public ApplicationUser User { get; set; }
    }
}
