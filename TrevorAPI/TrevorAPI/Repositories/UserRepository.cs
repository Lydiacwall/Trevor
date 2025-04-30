using TrevorAPI.Configurations;
using TrevorAPI.Model;
using Microsoft.Extensions.Options;
using MongoDB.Bson;
using MongoDB.Driver;



namespace TrevorAPI.Repositories
{
    public class UserRepository : IUserRepository
    {
        private readonly IMongoCollection<User> _usersCollection;

        public UserRepository(IOptions<DatabaseSettings> databaseSettings)
        {
            var mongoClient = new MongoClient(databaseSettings.Value.ConnectionURI);
            var mongoDb = mongoClient.GetDatabase(databaseSettings.Value.DatabaseName);
            _usersCollection = mongoDb.GetCollection<User>(databaseSettings.Value.Collections["Users"]);
        }
        public async Task<List<User>> GetAllUsersAsync()
        {
            return await _usersCollection.Find(user => true).ToListAsync();
        }

        public async Task<User> GetUserByPasswordAndEmailAsync(string? password, string? email)
        {
            return await _usersCollection.Find<User>(user => user.Password == password && user.Email == email).FirstOrDefaultAsync();
        }

        public async Task CreateUserAsync(User user)
        {
            await _usersCollection.InsertOneAsync(user);
        }

        public async Task<User> GetUserByEmail(string? email)
        {
            return await _usersCollection.Find<User>(user => user.Email == email).FirstOrDefaultAsync();
        }
        public async Task UpdateUserAsync(User user)
        {
            var filter = Builders<User>.Filter.Eq(u => u.Id, user.Id);
            await _usersCollection.ReplaceOneAsync(filter, user, new ReplaceOptions { IsUpsert = false });
        }

        public async Task<User> FindUserByIdAsync(string id)
        {
            return await _usersCollection.Find<User>(user => user.Id == id).FirstOrDefaultAsync();
        }

        public async Task<string> GetUsernameById(string id)
        {
            User user = await _usersCollection.Find<User>(user => user.Id == id).FirstOrDefaultAsync();
            return user.FirstName + " " + user.LastName; 
        }

        public async Task<string> GetEmailById(string id)
        {
            User user = await _usersCollection.Find<User>(user => user.Id == id).FirstOrDefaultAsync();
            return user.Email;
        }
    }
}
