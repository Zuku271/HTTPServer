package paczka;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.fasterxml.jackson.databind.node.IntNode;

public class ItemDeserializer extends StdDeserializer<Config>
{ 
	 
    public ItemDeserializer()
    { 
        this(null); 
    } 
 
    public ItemDeserializer(Class<?> vc)
    { 
        super(vc); 
    }
 
    @Override
    public Config deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException, JsonProcessingException
    {
        JsonNode node = jp.getCodec().readTree(jp);
        int id = (Integer) ((IntNode) node.get("id")).numberValue();
        String itemName = node.get("itemName").asText();
        int userId = (Integer) ((IntNode) node.get("createdBy")).numberValue();
 
        return new Config(id, itemName, new User(userId, null));
    }
}