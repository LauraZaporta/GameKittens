namespace API.GameKittens.DTO
{
    public class GivePointsDTO
    {
        public string UserId { get; set; }
        public string TargetUserId { get; set; }
        public int PointsToGive { get; set; }
    }
}
