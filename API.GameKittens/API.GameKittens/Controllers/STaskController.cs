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
    public class STaskController : ControllerBase
    {
        private readonly AppDbContext _context;
        private readonly ILogger<STaskController> _logger;

        public STaskController(AppDbContext context, ILogger<STaskController> logger)
        {
            _context = context;
            _logger = logger;
        }

        [AllowAnonymous]
        [HttpGet("TestUserController")]
        public IActionResult helloClient()
        {
            return Ok("Hello world");
        }
        /*
         public int Id { get; set; }
        public int ValidationVotes { get; set; }
        public string Title { get; set; }
        public string Description { get; set; }
        public string ImageURL { get; set; }

        // FK to ApplicationUser
        public string UserId { get; set; }
         */
        [HttpGet]
        public async Task<ActionResult<IEnumerable<STaskGetDTO>>> GetAllSTasks()
        {
            var tasks = await _context.STasks
                .Select(f => new STaskGetDTO
                {
                    Id = f.Id,
                    ValidationVotes = f.ValidationVotes,
                    Title = f.Title,
                    Description = f.Description,
                    ImageURL = f.ImageURL,
                    UserId = f.UserId
                })
                .Include(u => u.UserId)
                .ToListAsync();

            return Ok(tasks);
        }
    }
}

/*
HttpGet() GetAllTasks
HttpGet(id) GetTaskById
HttpGet(employee/id) GetTaskByUserId
HttpPost() NewTask
HttpDelete(id) DeleteTask
HttpPut(id) EditTask
HttpPatch(id) TaskValidate

 */