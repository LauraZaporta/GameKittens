using API.GameKittens.Models;
using System.ComponentModel.DataAnnotations.Schema;
using System.ComponentModel.DataAnnotations;

namespace API.GameKittens.DTO
{
    public class STaskInsertDTO
    {
        public string Title { get; set; }
        public string Description { get; set; }
        public string ImageURL { get; set; }

        // FK to ApplicationUser
        public string UserId { get; set; }
    }
}
