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

        //[Authorize]
        [HttpGet]
        public async Task<ActionResult<IEnumerable<UserGetDTO>>> GetAllUsers()
        {
            var users = await _context.Users
                .Select(u => new UserGetDTO
                {
                    Id = u.Id,
                    Username = u.UserName,
                    Name = u.Name,
                    Surename = u.Surename,
                    DNI = u.DNI,
                    Email = u.Email,
                    PhoneNumber = u.PhoneNumber,
                    Money = u.Money,
                    Points = u.Points
                })
                .ToListAsync();

            return Ok(users);
        }

        //[Authorize]
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
                Id = user.Id,
                Username = user.UserName,
                Name = user.Name,
                Surename = user.Surename,
                DNI = user.DNI,
                Email = user.Email,
                PhoneNumber = user.PhoneNumber,
                Money = user.Money,
                Points = user.Points

            };

            return Ok(userDTO);
        }

        //[Authorize(Roles = "Admin, Boss")]
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

        //[Authorize(Roles = "Admin, Boss")]
        [HttpPut("{id}")]
        public async Task<IActionResult> PutUser(string id, ApplicationUser user)
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

        //[Authorize]
        [HttpPost("GivePoints")]
        public async Task<IActionResult> GivePoints([FromBody] GivePointsDTO dto)
        {
            var targetUser = await _context.Users.FindAsync(dto.TargetUserId);
            if (targetUser == null)
            {
                return NotFound("Target user not found");
            }

            if (dto.PointsToGive <= 0)
            {
                return BadRequest("Points must be greater than zero");
            }

            targetUser.Points += dto.PointsToGive;
            await _context.SaveChangesAsync();

            return Ok(new { message = "Points given successfully", newTotalPoints = targetUser.Points });
        }

        private bool UserExists(string id)
        {
            return _context.Users.Any(u => u.Id == id);
        }
    }
}