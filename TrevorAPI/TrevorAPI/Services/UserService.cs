
using TrevorAPI.Forms;
using TrevorAPI.Model;
using TrevorAPI.Repositories;

namespace TrevorAPI.Services
{
    public class UserService : IUserService
    {
        
            private readonly IUserRepository _usersRepository;
            public UserService(IUserRepository userRepository)
            {
                _usersRepository = userRepository;
            }
        public async Task<User> GetUserInLoginAsync(string? password, string? email)
        {
            User user = await _usersRepository.GetUserByPasswordAndEmailAsync(password, email);
            return user;
        }


        public async Task<User?> CreateUserAsync(SignUpModelForm model)
            {
                User user = new User();

                user.FirstName = model.FirstName;
                user.LastName = model.LastName;
                user.Password = model.Password;
                user.Email = model.Email;

                await _usersRepository.CreateUserAsync(user);

                return await _usersRepository.GetUserByEmail(model.Email);
            }

            public async Task<User?> FindUserByEmailAsync(string? email)
            {
                //Search if there is another user with the same email
                return await _usersRepository.GetUserByEmail(email);
            }

            
        }
    
}
