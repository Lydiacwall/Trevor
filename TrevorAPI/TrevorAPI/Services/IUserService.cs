using TrevorAPI.Forms;
using TrevorAPI.Model;

namespace TrevorAPI.Services
{
    public interface IUserService
    {
        public Task<User> GetUserInLoginAsync(string? password, string? email);
        public Task<User?> CreateUserAsync(SignUpModelForm model);

        public Task<User?> FindUserByEmailAsync(string? email);

       
    }
}
