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
    public class STaskController : ControllerBase
    {
        private readonly AppDbContext _context;

        public STaskController(AppDbContext context)
        {
            _context = context;
        }

        [AllowAnonymous]
        [HttpGet("TestSTaskController")]
        public IActionResult helloClient()
        {
            return Ok("Hello world");
        }

        [Authorize]
        [HttpGet]
        public async Task<ActionResult<IEnumerable<STaskGetDTO>>> GetAllSTasks()
        {
            var sTasks = await _context.STasks
                .Select(t => new STaskGetDTO
                {
                    Id = t.Id,
                    ValidationVotes = t.ValidationVotes,
                    Title = t.Title,
                    Description = t.Description,
                    ImageURL = t.ImageURL,
                    UserId = t.UserId,
                    UserName = $"{t.User.Name} + {t.User.Surename}"
                })
                .ToListAsync();

            return Ok(sTasks);
        }

        [Authorize]
        [HttpGet("{id}")]
        public async Task<ActionResult<STaskGetDTO>> GetSTaskById(int id)
        {
            var task = await _context.STasks.FindAsync(id);

            if (task == null)
            {
                return NotFound();
            }

            return Ok(task);
        }

        [Authorize(Roles = "User, Admin")]
        [HttpPost]
        public async Task<ActionResult<STask>> PostSTask(STaskInsertDTO staskDTO)
        {
            // Verificar si el user existeix
            var user = await _context.Users.FindAsync(staskDTO.UserId);
            if (user == null)
            {
                return NotFound("User not found.");
            }

            var sTask = new STask
            {
                Title = staskDTO.Title,
                Description = staskDTO.Description,
                ValidationVotes = 0,
                ImageURL = staskDTO.ImageURL,
                UserId = staskDTO.UserId
            };

            try
            {
                await _context.STasks.AddAsync(sTask);
                await _context.SaveChangesAsync();
            }
            catch (DbUpdateException ex)
            {
                return BadRequest(ex);
            }


            return CreatedAtAction(nameof(GetSTaskById), new { id = sTask.Id }, staskDTO);
            //return Ok(sTask);
        }

        [Authorize(Roles = "Admin, Boss")]
        [HttpDelete("delete/{id}")]
        public async Task<IActionResult> DeleteSTask(int id)
        {
            var sTask = await _context.STasks.FindAsync(id);
            if (sTask == null)
            {
                return NotFound();
            }
            try
            {
                _context.STasks.Remove(sTask);
                await _context.SaveChangesAsync();
            }
            catch (DbUpdateException ex)
            {
                return BadRequest(ex);
            }
            return NoContent();
        }

        [Authorize(Roles = "Admin")]
        [HttpPut("put/{id}")]
        public async Task<IActionResult> PutSTask(int id, STask sTask)
        {
            if (id != sTask.Id)
            {
                return BadRequest();
            }

            _context.Entry(sTask).State = EntityState.Modified;

            try
            {
                await _context.SaveChangesAsync();
            }
            catch (DbUpdateConcurrencyException)
            {
                if (!STaskExists(id))
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

        private bool STaskExists(int id)
        {
            return _context.STasks.Any(t => t.Id == id);
        }
    }
}

/*
HttpGet(employee/id) GetTaskByUserId
HttpPatch(id) bool TaskValidate
*/