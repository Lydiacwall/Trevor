using MongoDB.Bson.Serialization.Attributes;

namespace TrevorAPI.Model
{
    [BsonIgnoreExtraElements] // This will instruct the serializer to skip unmapped fields.
    public class User
    {
        [BsonId]
        [BsonRepresentation(MongoDB.Bson.BsonType.ObjectId)]
        public string? Id { get; set; }

        [BsonElement("firstName")]
        public string? FirstName { get; set; } = null;
        [BsonElement("lastName")]
        public string? LastName { get; set; } = null;

        [BsonElement("password")]
        public string? Password { get; set; } = null;
        [BsonElement("email")]
        public string? Email { get; set; } = null;

    }
        
    
     
}
