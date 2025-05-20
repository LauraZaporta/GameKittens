using API.GameKittens.Models;

namespace API.GameKittens.DTO
{
    public class UserRegisterDTO
    {
        public string Username { get; set; }
        public string Name { get; set; }
        public string Surename { get; set; }
        public string DNI { get; set; }
        public string Email { get; set; }
        public string PhoneNumber { get; set; }
        public string Password { get; set; }
    }
}
