using System.IO;
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
    public class UserController : ControllerBase
    {
        private readonly AppDbContext _context;

        public UserController(AppDbContext context)
        {
            _context = context;
        }

        [AllowAnonymous]
        [HttpGet("TestUserController")]
        public IActionResult helloClient()
        {
            return Ok("Hello world");
        }

        [HttpGet]
        public async Task<ActionResult<IEnumerable<UserGetDTO>>> GetAllUsers()
        {
            var users = await _context.Users
                .Select(f => new UserGetDTO
                {
                    Name = f.Name,
                    Surename = f.Surename,
                    Money = f.Money,
                    Points = f.Points
                })
                .ToListAsync();

            return Ok(users);
        }

        [HttpGet("{id}")]
        public async Task<ActionResult<UserGetDTO>> GetUserById(string id)
        {
            var user = await _context.Users.FindAsync(id);

            if (user == null)
            {
                return NotFound();
            }

            var userDTO = new UserGetDTO
            {
                Name = user.Name,
                Surename = user.Surename

            };

            return Ok(userDTO);
        }

        [HttpDelete("{id}")]
        public async Task<IActionResult> DeleteUser(string id)
        {
            var user = await _context.Users.FindAsync(id);
            if (user == null)
            {
                return NotFound();
            }

            _context.Users.Remove(user);
            await _context.SaveChangesAsync();

            return NoContent();
        }

        [HttpPut("{id}")]
        public async Task<IActionResult> PutUser(string id, UserPutDTO user)
        {
            if (id != user.Id)
            {
                return BadRequest();
            }

            _context.Entry(user).State = EntityState.Modified;

            try
            {
                await _context.SaveChangesAsync();
            }
            catch (DbUpdateConcurrencyException)
            {
                if (!UserExists(id))
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

        // Ask Laura why do that? Codi irevelant. Rachel clean code pls >:c
        [HttpPatch]
        public async Task<IActionResult> AddPointsToEmployee(string id, int points)
        {
            var user = await _context.Users.FindAsync(id);

            if (user == null)
            {
                return NotFound();
            }

            //user.Points = user.Points + points;
            //user.Points = points;

            _context.Entry(user).State = EntityState.Modified;

            return Ok();
        }

        private bool UserExists(string id)
        {
            return _context.Users.Any(u => u.Id == id);
        }
    }
}