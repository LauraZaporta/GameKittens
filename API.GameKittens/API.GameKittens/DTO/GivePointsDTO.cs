namespace API.GameKittens.DTO
{
    public class GivePointsDTO
    {
        public string TargetUserId { get; set; }
        public int PointsToGive { get; set; }
    }
}
