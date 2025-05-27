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

        [Authorize]
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
                    NormalImage = $"{Request.Scheme}://{Request.Host}/{p.IdleImage}",
                    PetImage = $"{Request.Scheme}://{Request.Host}/{p.PetImage}",
                    HungryImage = $"{Request.Scheme}://{Request.Host}/{p.HungryImage}",
                    TooHungryImage = $"{Request.Scheme}://{Request.Host}/{p.ToHungryImage}",
                    UserId = p.UserId
                })
                .ToListAsync();

            return Ok(pets);
        }

        [Authorize]
        [HttpGet("{id}")]
        public async Task<ActionResult<PetGetDTO>> GetPetById(int id)
        {
            var p = await _context.Pets.FindAsync(id);

            if (p == null)
            {
                return NotFound();
            }

            PetGetDTO petGet = new PetGetDTO
            {
                Animal = p.Animal,
                Name = p.Name,
                PetState = p.PetState,
                NormalImage = $"{Request.Scheme}://{Request.Host}/{p.IdleImage}",
                PetImage = $"{Request.Scheme}://{Request.Host}/{p.PetImage}",
                HungryImage = $"{Request.Scheme}://{Request.Host}/{p.HungryImage}",
                TooHungryImage = $"{Request.Scheme}://{Request.Host}/{p.ToHungryImage}",
                UserId = p.UserId
            };

            return Ok(petGet);
        }

        [Authorize(Roles = "Admin")]
        [HttpPost]
        [Route("api/pets")]
        [ProducesResponseType(StatusCodes.Status201Created)]
        [ProducesResponseType(StatusCodes.Status400BadRequest)]
        [ProducesResponseType(StatusCodes.Status404NotFound)]
        public async Task<ActionResult<PetGetDTO>> PostPet([FromForm] PetInsertDTO petDTO)
        {
            var user = await _context.Users.FindAsync(petDTO.UserId);
            if (user == null)
                return NotFound(new { message = "Usuari no trobat." });

            try
            {
                string normalImagePath = string.Empty;
                string petImagePath = string.Empty;
                string hungryImagePath = string.Empty;
                string tooHungryImagePath = string.Empty;

                var imagesFolder = Path.Combine(Directory.GetCurrentDirectory(), "wwwroot", "images");
                Directory.CreateDirectory(imagesFolder);

                if (petDTO.NormalImage != null && petDTO.NormalImage.Length > 0)
                {
                    var uniqueName = Guid.NewGuid() + Path.GetExtension(petDTO.NormalImage.FileName);
                    var fullPath = Path.Combine(imagesFolder, uniqueName);
                    using (var stream = new FileStream(fullPath, FileMode.Create))
                    {
                        await petDTO.NormalImage.CopyToAsync(stream);
                    }
                    normalImagePath = Path.Combine("images", uniqueName).Replace("\\", "/");
                }

                if (petDTO.PetImage != null && petDTO.PetImage.Length > 0)
                {
                    var uniqueName = Guid.NewGuid() + Path.GetExtension(petDTO.PetImage.FileName);
                    var fullPath = Path.Combine(imagesFolder, uniqueName);
                    using (var stream = new FileStream(fullPath, FileMode.Create))
                    {
                        await petDTO.PetImage.CopyToAsync(stream);
                    }
                    petImagePath = Path.Combine("images", uniqueName).Replace("\\", "/");
                }

                if (petDTO.HungryImage != null && petDTO.HungryImage.Length > 0)
                {
                    var uniqueName = Guid.NewGuid() + Path.GetExtension(petDTO.HungryImage.FileName);
                    var fullPath = Path.Combine(imagesFolder, uniqueName);
                    using (var stream = new FileStream(fullPath, FileMode.Create))
                    {
                        await petDTO.HungryImage.CopyToAsync(stream);
                    }
                    hungryImagePath = Path.Combine("images", uniqueName).Replace("\\", "/");
                }

                if (petDTO.TooHungryImage != null && petDTO.TooHungryImage.Length > 0)
                {
                    var uniqueName = Guid.NewGuid() + Path.GetExtension(petDTO.TooHungryImage.FileName);
                    var fullPath = Path.Combine(imagesFolder, uniqueName);
                    using (var stream = new FileStream(fullPath, FileMode.Create))
                    {
                        await petDTO.TooHungryImage.CopyToAsync(stream);
                    }
                    tooHungryImagePath = Path.Combine("images", uniqueName).Replace("\\", "/");
                }

                var pet = new Pet
                {
                    Name = petDTO.Name,
                    Animal = petDTO.Animal,
                    PetState = petDTO.PetState,
                    IdleImage = normalImagePath,
                    PetImage = petImagePath,
                    HungryImage = hungryImagePath,
                    ToHungryImage = tooHungryImagePath,
                    UserId = petDTO.UserId
                };

                _context.Pets.Add(pet);
                await _context.SaveChangesAsync();

                var response = new PetGetDTO
                {
                    Id = pet.Id,
                    Name = pet.Name,
                    Animal = pet.Animal,
                    PetState = pet.PetState,
                    NormalImage = $"{Request.Scheme}://{Request.Host}/{normalImagePath}",
                    PetImage = $"{Request.Scheme}://{Request.Host}/{petImagePath}",
                    HungryImage = $"{Request.Scheme}://{Request.Host}/{hungryImagePath}",
                    TooHungryImage = $"{Request.Scheme}://{Request.Host}/{tooHungryImagePath}",
                    UserId = pet.UserId
                };

                return CreatedAtAction(nameof(GetPetById), new { id = pet.Id }, response);
            }
            catch (Exception ex)
            {
                return BadRequest(new { message = "Error intern durant la inserció." });
            }
        }

        [Authorize(Roles = "Admin")]
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
                // Esborra la imatge normal
                string? normalImageFullPath = Path.Combine(Directory.GetCurrentDirectory(), "wwwroot", pet.IdleImage);
                if (System.IO.File.Exists(normalImageFullPath))
                {
                    System.IO.File.Delete(normalImageFullPath);
                }
                // Esborra la imatge pet
                string? petImageFullPath = Path.Combine(Directory.GetCurrentDirectory(), "wwwroot", pet.PetImage);
                if (System.IO.File.Exists(petImageFullPath))
                {
                    System.IO.File.Delete(petImageFullPath);
                }

                // Esborra la imatge hungry
                string? hungryImageFullPath = Path.Combine(Directory.GetCurrentDirectory(), "wwwroot", pet.HungryImage);
                if (System.IO.File.Exists(hungryImageFullPath))
                {
                    System.IO.File.Delete(hungryImageFullPath);
                }

                // Esborra la imatge too hungry
                string? tooHungryImageFullPath = Path.Combine(Directory.GetCurrentDirectory(), "wwwroot", pet.ToHungryImage);
                if (System.IO.File.Exists(tooHungryImageFullPath))
                {
                    System.IO.File.Delete(tooHungryImageFullPath);
                }


                _context.Pets.Remove(pet);
                await _context.SaveChangesAsync();
            }
            catch (DbUpdateException ex)
            {
                return BadRequest(ex);
            }
            return NoContent();
        }

        [Authorize(Roles = "Admin, User")]
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

        [Authorize]
        [HttpGet("byUser/{id}")]
        public async Task<ActionResult<PetGetDTO>> GetPetByUserId(string id)
        {
            var p = await _context.Pets.Where(p => p.UserId == id).FirstOrDefaultAsync();

            if (p == null)
            {
                return NotFound();
            }

            PetGetDTO petGet = new PetGetDTO
            {
                Animal = p.Animal,
                Name = p.Name,
                PetState = p.PetState,
                NormalImage = $"{Request.Scheme}://{Request.Host}/{p.IdleImage}",
                PetImage = $"{Request.Scheme}://{Request.Host}/{p.PetImage}",
                HungryImage = $"{Request.Scheme}://{Request.Host}/{p.HungryImage}",
                TooHungryImage = $"{Request.Scheme}://{Request.Host}/{p.ToHungryImage}",
                UserId = p.UserId
            };

            return Ok(petGet);
        }

        private bool PetExists(int id)
        {
            return _context.Pets.Any(p => p.Id == id);
        }
    }
}