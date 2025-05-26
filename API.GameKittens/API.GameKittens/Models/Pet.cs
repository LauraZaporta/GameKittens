﻿using System.ComponentModel.DataAnnotations.Schema;
using System.ComponentModel.DataAnnotations;
using System.Text.Json.Serialization;

namespace API.GameKittens.Models
{
    [Table("Pets")]
    public class Pet
    {
        [Key]
        [DatabaseGenerated(DatabaseGeneratedOption.Identity)]
        public int Id { get; set; }
        public int Animal { get; set; }
        public string Name { get; set; }
        public bool PetState { get; set; }
        //public int Hunger { get; set; }
        public string? IdleImage { get; set; }
        public string? PetImage { get; set; }
        public string? HungryImage { get; set; }
        public string? ToHungryImage { get; set; }

        
        // FK to ApplicationUser (1:1)
        public string UserId { get; set; }
        [ForeignKey("UserId")]
        [JsonIgnore]
        public ApplicationUser? User { get; set; }
    }
}
