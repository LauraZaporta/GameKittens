using API.GameKittens.Models;
using Microsoft.AspNetCore.Identity.EntityFrameworkCore;
using Microsoft.EntityFrameworkCore;

namespace API.GameKittens.Context
{
    public class AppDbContext : IdentityDbContext<ApplicationUser>
    {
        public AppDbContext(DbContextOptions<AppDbContext> options) : base(options) { }

        public DbSet<STask> STasks { get; set; }
        //public DbSet<Pet> Pets { get; set; }
        //public DbSet<Accessory> Accessories { get; set; }

        protected override void OnModelCreating(ModelBuilder modelBuilder)
        {
            base.OnModelCreating(modelBuilder);

            /*// Relación 1:1 entre User y Pet
            modelBuilder.Entity<ApplicationUser>()
                .HasOne(u => u.Pet)
                .WithOne(p => p.User)
                .HasForeignKey<Pet>(p => p.UserId)
                .OnDelete(DeleteBehavior.Cascade);*/

            // Relación 1:N entre User y STask
            modelBuilder.Entity<ApplicationUser>()
                .HasMany(u => u.Tasks)
                .WithOne(t => t.User)
                .HasForeignKey(t => t.UserId)
                .OnDelete(DeleteBehavior.Cascade);

            /*
            // Relación: Pet -> Accessory (equipado)
            modelBuilder.Entity<Pet>()
                .HasOne(p => p.Accessory)
                .WithMany(a => a.EquippedByPets)
                .HasForeignKey(p => p.AccessoryId)
                .OnDelete(DeleteBehavior.SetNull);

            // Relación: Pet -> Accessory (disponibles - muchos a muchos)
            modelBuilder.Entity<Pet>()
                .HasMany(p => p.AvailableAccessories)
                .WithMany(a => a.AvailableInPets)
                .UsingEntity(j => j.ToTable("PetAccessories"));  // Tabla intermedia
            */

        }
    }
}
