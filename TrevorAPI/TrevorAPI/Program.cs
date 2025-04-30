
using TrevorAPI.Configurations;
using TrevorAPI.Repositories;
using TrevorAPI.Services;
using Microsoft.AspNetCore.Http.Connections;

using MongoDB.Bson.Serialization;


var builder = WebApplication.CreateBuilder(args);

// Add services to the container.
builder.Services.Configure<DatabaseSettings>(builder.Configuration.GetSection("MongoDatabase"));

// Register the custom tuple serializer
BsonSerializer.RegisterSerializer(typeof(Tuple<string, string>), new TupleSerializer<string, string>());

// User
builder.Services.AddSingleton<IUserService, UserService>();
builder.Services.AddSingleton<IUserRepository, UserRepository>();

builder.Services.AddControllers();
builder.Services.AddEndpointsApiExplorer();
builder.Services.AddSwaggerGen();
builder.Services.AddSignalR();

builder.WebHost.ConfigureKestrel(serverOptions =>
{
    serverOptions.Listen(System.Net.IPAddress.Any, 5057);  // Listen for HTTP on port 5057
});

var app = builder.Build();

app.UseCors("AllowAll");

// Configure the HTTP request pipeline.
if (app.Environment.IsDevelopment())
{
    app.UseSwagger();
    app.UseSwaggerUI();
}


/*app.UseHttpsRedirection(); // Ensure this is uncommented to enforce HTTPS redirection
*/
app.UseAuthorization();
app.MapControllers();
app.Run();


