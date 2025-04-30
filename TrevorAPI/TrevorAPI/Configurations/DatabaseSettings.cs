namespace TrevorAPI.Configurations
{

    public class DatabaseSettings
    {
        public string? ConnectionURI { get; set; } = null;
        public string? ConnectionString { get; set; } = null;
        public string? DatabaseName { get; set; } = null;
        public required Dictionary<string, string> Collections { get; set; }
    }
}



