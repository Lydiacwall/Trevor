using System.Text.Json.Serialization;
namespace TrevorAPI.Forms
{
    public class SignUpModelForm
    {
        [JsonPropertyName("firstName")]
        public string? FirstName { get; set; }
        [JsonPropertyName("lastName")]
        public string? LastName { get; set; }

        [JsonPropertyName("Password")]
        public string? Password { get; set; }
        [JsonPropertyName("email")]
        public string? Email { get; set; }
        

        public bool IsValid
        {
            get
            {
                return !string.IsNullOrEmpty(FirstName) &&
                       !string.IsNullOrEmpty(LastName) &&
                       !string.IsNullOrEmpty(Email) &&
                       !string.IsNullOrEmpty(Password);
                     
            }
        }
    }
}
