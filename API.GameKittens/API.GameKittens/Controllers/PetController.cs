using API.GameKittens.Context;
using API.GameKittens.DTO;
using API.GameKittens.Models;
using Microsoft.AspNetCore.Authorization;
using Microsoft.AspNetCore.Http;
using Microsoft.AspNetCore.Mvc;
using Microsoft.EntityFrameworkCore;

namespace API.GameKittens.Controllers
{
    [Route("api/[controller]")]
    [ApiController]
    public class PetController : ControllerBase
    {
        private readonly AppDbContext _context;

        public PetController(AppDbContext context)
        {
            _context = context;
        }

        [AllowAnonymous]
        [HttpGet("TestPetController")]
        public IActionResult helloClient()
        {
            return Ok("Helo client");
        }


        [HttpGet]
        public async Task<ActionResult<IEnumerable<PetGetDTO>>> GetAllPets()
        {
            var pets = await _context.Pets
                .Select(p => new PetGetDTO
                {
                    Id = p.Id,
                    Animal = p.Animal,
                    Name = p.Name,
                    PetState = p.PetState,
                    Hunger = p.Hunger,
                    IdleImage = p.IdleImage,
                    PetImage = p.PetImage,
                    HungryImage = p.HungryImage,
                    ToHungryImage = p.ToHungryImage,
                    AccessoryId = p.Accessory.Id,
                    UserId = p.UserId
                })
                .ToListAsync();

            return Ok(pets);
        }

        [HttpGet("{id}")]
        public async Task<ActionResult<Pet>> GetPetById(int id)
        {
            var pet = await _context.Pets.FindAsync(id);

            if (pet == null)
            {
                return NotFound();
            }

            return Ok(pet);
        }

        [HttpPost]
        public async Task<ActionResult<Pet>> PostPet(PetInsertDTO petDTO)
        {
            // Verificar si el user existeix
            var user = await _context.Users.FindAsync(petDTO.UserId);
            // Verificar si el accessori existeix
            var accessory = await _context.Accessories.FindAsync(petDTO.AccessoryId);
            
            if (user == null)
            {
                return NotFound("User not found.");
            }
            if (accessory == null)
            {
                return NotFound("Accessory not found.");
            }


            var pet = new Pet
            {
                Name = petDTO.Name,
                Animal = petDTO.Animal,
                PetState = petDTO.PetState,
                Hunger = petDTO.Hunger,
                IdleImage = petDTO.IdleImage,
                PetImage = petDTO.PetImage,
                HungryImage = petDTO.HungryImage,
                ToHungryImage = petDTO.ToHungryImage,
                AccessoryId = petDTO.AccessoryId,
                UserId = petDTO.UserId
            };

            try
            {
                await _context.Pets.AddAsync(pet);
                await _context.SaveChangesAsync();
            }
            catch (DbUpdateException ex)
            {
                return BadRequest(ex);
            }


            return CreatedAtAction(nameof(GetPetById), new { id = pet.Id }, pet);
            //return Ok(film);
        }

        [HttpDelete("delete/{id}")]
        public async Task<IActionResult> DeletePet(int id)
        {
            var pet = await _context.Pets.FindAsync(id);
            if (pet == null)
            {
                return NotFound();
            }
            try
            {
                _context.Pets.Remove(pet);
                await _context.SaveChangesAsync();
            }
            catch (DbUpdateException ex)
            {
                return BadRequest(ex);
            }
            return NoContent();
        }

        [HttpPut("put/{id}")]
        public async Task<IActionResult> PutPet(int id, Pet pet)
        {
            if (id != pet.Id)
            {
                return BadRequest();
            }

            _context.Entry(pet).State = EntityState.Modified;

            try
            {
                await _context.SaveChangesAsync();
            }
            catch (DbUpdateConcurrencyException)
            {
                if (!PetExists(id))
                {
                    return NotFound();
                }
                else
                {
                    throw;
                }
            }

            return NoContent();
        }

        private bool PetExists(int id)
        {
            return _context.Pets.Any(p => p.Id == id);
        }
    }
}