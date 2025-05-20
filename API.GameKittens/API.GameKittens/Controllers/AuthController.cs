using API.GameKittens.DTO;
using API.GameKittens.Models;
using Microsoft.AspNetCore.Http;
using Microsoft.AspNetCore.Identity;
using Microsoft.AspNetCore.Mvc;
using Microsoft.IdentityModel.Abstractions;

namespace API.GameKittens.Controllers
{
    [Route("api/[controller]")]
    [ApiController]
    public class AuthController : ControllerBase
    {
        private readonly UserManager<ApplicationUser> _userManager;
        private readonly ILogger<AuthController> _logger;
        private readonly IConfiguration _configuration;
        public AuthController(UserManager<ApplicationUser> userManager, ILogger<AuthController> logger, IConfiguration configuration)
        {
            _userManager = userManager;
            _logger = logger;
            _configuration = configuration;
        }

        [HttpPost("registre")]
        public async Task<IActionResult> Register([FromBody] UserRegisterDTO model)
        {
            var usuari = new ApplicationUser
            { 
                UserName = model.Username,
                Name = model.Name,
                Surename = model.Surename,
                DNI = model.DNI,
                Email = model.Email,
                PhoneNumber = model.PhoneNumber,
                Points = 0,
                Money = 0
            };
            var resultat = await _userManager.CreateAsync(usuari, model.Password);

            if (resultat.Succeeded)
                return Ok("Usuari registrat");

            return BadRequest(resultat.Errors);
        }
    }
}
