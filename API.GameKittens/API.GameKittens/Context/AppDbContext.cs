using API.GameKittens.Models;
using Microsoft.AspNetCore.Identity.EntityFrameworkCore;
using Microsoft.EntityFrameworkCore;

namespace API.GameKittens.Context
{
    public class AppDbContext : IdentityDbContext<ApplicationUser>
    {
        public AppDbContext(DbContextOptions<AppDbContext> options) : base(options) { }

        public DbSet<STask> STasks { get; set; }
        public DbSet<STaskVote> STaskVotes { get; set; }

        protected override void OnModelCreating(ModelBuilder modelBuilder)
        {
            base.OnModelCreating(modelBuilder);


            // Relación 1:N entre User y STask
            modelBuilder.Entity<ApplicationUser>()
                .HasMany(u => u.Tasks)
                .WithOne(t => t.User)
                .HasForeignKey(t => t.UserId)
                .OnDelete(DeleteBehavior.Restrict);
            // Relación 1:N entre ApplicationUser y STaskVote
            modelBuilder.Entity<STaskVote>()
                .HasOne(v => v.User)
                .WithMany()
                .HasForeignKey(v => v.UserId)
                .OnDelete(DeleteBehavior.Restrict);

            // Relación 1:N entre STask y STaskVote
            modelBuilder.Entity<STaskVote>()
                .HasOne(v => v.Task)
                .WithMany()
                .HasForeignKey(v => v.TaskId)
                .OnDelete(DeleteBehavior.Restrict);
        }
    }
}
