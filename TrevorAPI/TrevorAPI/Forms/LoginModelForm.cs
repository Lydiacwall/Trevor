using System.Text.Json.Serialization;
using System.Xml.Linq;

namespace TrevorAPI.Forms
{
    public class LoginModelForm
    {
        [JsonPropertyName("email")]
        public string? Email { get; set; }
        [JsonPropertyName("password")]
        public string? Password { get; set; }

        public bool IsValid
        {
            get
            {
                return !string.IsNullOrEmpty(Email) &&
                       !string.IsNullOrEmpty(Password);
            }
        }
    }
}
