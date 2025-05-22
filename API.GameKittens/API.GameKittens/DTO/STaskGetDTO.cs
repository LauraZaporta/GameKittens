using API.GameKittens.Models;

namespace API.GameKittens.DTO
{
    public class STaskGetDTO
    {
        public int Id { get; set; }
        public int ValidationVotes { get; set; }
        public string Title { get; set; }
        public string Description { get; set; }
        public string ImageURL { get; set; }

        public string UserId { get; set; }
        public string UserName { get; set; }
    }
}
