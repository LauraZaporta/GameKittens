using System.Threading.Tasks;
using API.GameKittens.Context;
using API.GameKittens.DTO.STask;
using API.GameKittens.Models;
using Microsoft.AspNetCore.Authorization;
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

        /// <summary>
        /// EndPoint to get all tasks from STask table
        /// </summary>
        /// <returns></returns>
        //[Authorize]
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
                    ImageURL = $"{Request.Scheme}://{Request.Host}/{t.ImageURL}",
                    UserId = t.UserId,
                    UserName = $"{t.User.Name} + {t.User.Surename}"
                })
                .ToListAsync();

            return Ok(sTasks);
        }

        //[Authorize]
        [HttpGet("{id}")]
        public async Task<ActionResult<STaskGetDTO>> GetSTaskById(int id)
        {
            var task = await _context.STasks.FindAsync(id);

            if (task == null)
            {
                return NotFound();
            }

            STaskGetDTO taskGet = new STaskGetDTO
            {
                Id = task.Id,
                ValidationVotes = task.ValidationVotes,
                Title = task.Title,
                Description = task.Description,
                ImageURL = $"{Request.Scheme}://{Request.Host}/{task.ImageURL}",
                UserId = task.UserId
            };

            return Ok(taskGet);
        }

        //[Authorize(Roles = "User, Admin")]
        [HttpPost]
        public async Task<ActionResult<STask>> PostSTask(STaskInsertDTO staskDTO)
        {
            // Verificar si el user existeix
            var user = await _context.Users.FindAsync(staskDTO.UserId);
            if (user == null)
            {
                return NotFound("User not found.");
            }

            try
            {
                string imagePath = string.Empty;

                // wwwroot per defecte és una carpeta pública
                var imagesFolder = Path.Combine(Directory.GetCurrentDirectory(), "wwwroot", "images");
                // Per si no existeix
                Directory.CreateDirectory(imagesFolder);

                // Genera un nom d'archiu amb identificador únic i amb l'extensió de la imatge enviada
                var uniqueFileName = Guid.NewGuid().ToString() + Path.GetExtension(staskDTO.ImageURL.FileName);
                // Construeix la ruta final del fitxer
                var filePath = Path.Combine(imagesFolder, uniqueFileName);

                using (var stream = new FileStream(filePath, FileMode.Create))
                {
                    await staskDTO.ImageURL.CopyToAsync(stream); // Copia la imatge donada dintre de la ruta filePath
                }

                // Guarda la ruta relativa com a URL a la BBDD
                imagePath = Path.Combine("images", uniqueFileName).Replace("\\", "/");

                var sTask = new STask
                {
                    Title = staskDTO.Title,
                    Description = staskDTO.Description,
                    ValidationVotes = 0,
                    ImageURL = imagePath,
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
            }
            catch (Exception ex)
            {
                return BadRequest($"Error en la inserció: {ex.Message}");
            }
        }

        //[Authorize(Roles = "Admin, Boss")]
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
                if (sTask.ImageURL != null) // Per esborrar la imatge de la carpeta images
                {
                    string? imageFullPath = Path.Combine(Directory.GetCurrentDirectory(), "wwwroot", sTask.ImageURL);
                    if (System.IO.File.Exists(imageFullPath))
                    {
                        System.IO.File.Delete(imageFullPath); // Espera una ruta absoluta
                    }
                }

                _context.STasks.Remove(sTask);
                await _context.SaveChangesAsync();
            }
            catch (DbUpdateException ex)
            {
                return BadRequest(ex);
            }
            return NoContent();
        }

        //[Authorize(Roles = "Admin")]
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

        //[Authorize]
        [HttpPost("like/{taskId}")]
        public async Task<IActionResult> LikeTask(int taskId, string userId)
        {
            var task = await _context.STasks.FindAsync(taskId);

            var user = await _context.Users.FindAsync(userId);
            var targetUser = await _context.Users.FindAsync(task.UserId);


            if (task == null) return NotFound();

            var existingVote = await _context.STaskVotes
                .FirstOrDefaultAsync(v => v.TaskId == taskId && v.UserId == userId);

            if (existingVote != null)
                return BadRequest("You already voted for this task.");

            _context.STaskVotes.Add(new STaskVote { UserId = userId, TaskId = taskId });
            task.ValidationVotes += 1;
            task.Validate = task.ValidationVotes > 3;

            // Añadir 10 puntos si son mas de 3 votos
            if (task.ValidationVotes > 3)
            {
                task.User.Points = targetUser.Points + 10;
                user.Points = user.Points - 10;
                _context.STasks.Remove(task);
            }

            await _context.SaveChangesAsync();

            return Ok(new { message = "Vote added." });
        }

        //[Authorize]
        [HttpPost("dislike/{taskId}")]
        public async Task<IActionResult> DislikeTask(int taskId, string userId)
        {
            var task = await _context.STasks.FindAsync(taskId);

            var user = await _context.Users.FindAsync(userId);


            if (task == null) return NotFound();

            var existingVote = await _context.STaskVotes
                .FirstOrDefaultAsync(v => v.TaskId == taskId && v.UserId == userId);

            if (existingVote == null)
                return BadRequest("You haven't voted for this task.");

            _context.STaskVotes.Remove(existingVote);
            task.ValidationVotes -= 1;
            task.Validate = task.ValidationVotes > 0;

            if (task.ValidationVotes < 0)
            {
                _context.STasks.Remove(task);
            }

            await _context.SaveChangesAsync();

            return Ok(new { message = "Vote removed." });
        }


        private bool STaskExists(int id)
        {
            return _context.STasks.Any(t => t.Id == id);
        }
    }
}