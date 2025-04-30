using TrevorAPI.Model;
namespace TrevorAPI.Repositories
{
    public interface IUserRepository
    {
        Task<List<User>> GetAllUsersAsync();
        Task<User> GetUserByPasswordAndEmailAsync(string? password, string? email);
        Task<User> GetUserByEmail(string? email);
        Task CreateUserAsync(User user);
        Task UpdateUserAsync(User user);
        Task<User> FindUserByIdAsync(string id);
        Task<string> GetUsernameById(string id);
        Task<string> GetEmailById(string id);
    }

}
