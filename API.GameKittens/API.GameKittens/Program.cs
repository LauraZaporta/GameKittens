using API.GameKittens.Context;
using Microsoft.EntityFrameworkCore;

internal class Program
{
    private static async Task Main(string[] args)
    {
        var builder = WebApplication.CreateBuilder(args);

        // Build delay
        await Task.Delay(3000);


        // Add services to the container.

        //Afegim DbContext
        var connectionString = builder.Configuration.GetConnectionString("DevelopmentConnection");
        object value = builder.Services.AddDbContext<AppDbContext>(options => options.UseSqlServer(connectionString));


        builder.Services.AddRazorPages();
        builder.Services.AddHttpContextAccessor();

        // Swagger
        builder.Services.AddSwaggerGen();

        // Controladors
        builder.Services.AddControllers();

        // Learn more about configuring OpenAPI at https://aka.ms/aspnet/openapi
        builder.Services.AddOpenApi();



        /*--------------*/
        var app = builder.Build();
        /*--------------*/


        // Configure the HTTP request pipeline.
        if (app.Environment.IsDevelopment())
        {
            app.MapOpenApi();
            app.UseSwagger();
            app.UseSwaggerUI();
        }

        app.UseHttpsRedirection();

        app.UseAuthorization();

        app.MapControllers();

        app.Run();
    }
}