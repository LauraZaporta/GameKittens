using API.GameKittens.Context;
using API.GameKittens.DTO;
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
            var films = await _context.Users
                .Select(f => new UserGetDTO
                {
                    Name = f.Name,
                    Surename = f.Surename,
                    Money = f.Money,
                    Points = f.Points
                })
                .ToListAsync();
            return Ok(films);
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


    }
}
/*
HttpGet() GetAllUsers
HttpGet(id) GetUserById
HttpPost() NewUser
HttpDelete(id) DeleteUser
HttpPut(id) EditEmployee
HttpPatch(id) AddPointsToEmployee

 */