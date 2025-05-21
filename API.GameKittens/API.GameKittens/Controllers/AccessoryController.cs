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
    public class AccessoryController : ControllerBase
    {
        private readonly AppDbContext _context;

        public AccessoryController(AppDbContext context)
        {
            _context = context;
        }

        [AllowAnonymous]
        [HttpGet("TestAccesoryController")]
        public IActionResult helloClient()
        {
            return Ok("Hello world");
        }

        [HttpGet]
        public async Task<ActionResult<IEnumerable<AccessoryGetDTO>>> GetAllAccessories()
        {
            var accessories = await _context.Accessories
                .Select(a => new AccessoryGetDTO
                {
                    Id = a.Id,
                    Name = a.Name,
                    ImageURL = a.ImageURL,
                    Price = a.Price
                })
                .ToListAsync();

            return Ok(accessories);
        }

        [HttpGet("{id}")]
        public async Task<ActionResult<AccessoryGetDTO>> GetAccessoryById(int id)
        {
            var accessory = await _context.Accessories.FindAsync(id);

            if (accessory == null)
            {
                return NotFound();
            }

            return Ok(accessory);
        }


        [HttpPost]
        public async Task<ActionResult<Accessory>> PostAccessory(AccessoryInsertDTO accessoryDTO)
        {
            var accessory = new Accessory
            {
                Name = accessoryDTO.Name,
                ImageURL = accessoryDTO.ImageURL,
                Price = accessoryDTO.Price
            };

            try
            {
                await _context.Accessories.AddAsync(accessory);
                await _context.SaveChangesAsync();
            }
            catch (DbUpdateException ex)
            {
                return BadRequest(ex);
            }


            return CreatedAtAction(nameof(GetAccessoryById), new { id = accessory.Id }, accessory);
            //return Ok(film);
        }

        [HttpDelete("delete/{id}")]
        public async Task<IActionResult> DeleteAccessory(int id)
        {
            var accessory = await _context.Accessories.FindAsync(id);
            if (accessory == null)
            {
                return NotFound();
            }
            try
            {
                _context.Accessories.Remove(accessory);
                await _context.SaveChangesAsync();
            }
            catch (DbUpdateException ex)
            {
                return BadRequest(ex);
            }
            return NoContent();
        }

        [HttpPut("put/{id}")]
        public async Task<IActionResult> PutAccessory(int id, Accessory accessory)
        {
            if (id != accessory.Id)
            {
                return BadRequest();
            }

            _context.Entry(accessory).State = EntityState.Modified;

            try
            {
                await _context.SaveChangesAsync();
            }
            catch (DbUpdateConcurrencyException)
            {
                if (!AccessoryExists(id))
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

        private bool AccessoryExists(int id)
        {
            return _context.Accessories.Any(e => e.Id == id);
        }
    }
}