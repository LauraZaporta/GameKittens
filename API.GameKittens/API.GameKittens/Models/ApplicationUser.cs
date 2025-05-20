using Microsoft.AspNetCore.Identity;

namespace API.GameKittens.Models
{
    public class ApplicationUser : IdentityUser
    {
        public string Name { get; set; }
        public string Surename { get; set; }
        public string DNI { get; set; }
        public int Points { get; set; }
        public int Money { get; set; }

        // 1:N con tareas
        public List<STask> Tasks { get; set; }

        // 1:1 con mascota
        public Pet Pet { get; set; }
    }
}