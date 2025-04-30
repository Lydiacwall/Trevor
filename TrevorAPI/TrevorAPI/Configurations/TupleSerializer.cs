
namespace TrevorAPI.Configurations
{

    using MongoDB.Bson;
    using MongoDB.Bson.Serialization;
    using MongoDB.Bson.Serialization.Serializers;
    public class TupleSerializer<T1, T2> : SerializerBase<Tuple<T1, T2>>
    {
        public override Tuple<T1, T2> Deserialize(BsonDeserializationContext context, BsonDeserializationArgs args)
        {
            var bsonDocument = BsonSerializer.Deserialize<BsonDocument>(context.Reader);
            var item1 = bsonDocument["Item1"].AsString;
            var item2 = bsonDocument["Item2"].AsString;
            return new Tuple<T1, T2>((T1)(object)item1, (T2)(object)item2);
        }

        public override void Serialize(BsonSerializationContext context, BsonSerializationArgs args, Tuple<T1, T2> value)
        {
            var bsonDocument = new BsonDocument
        {
            { "Item1", new BsonString(value.Item1.ToString()) },
            { "Item2", new BsonString(value.Item2.ToString()) }
        };
            BsonSerializer.Serialize(context.Writer, bsonDocument);
        }
    }
}
