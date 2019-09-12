package paczka;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
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
        ObjectMapper mapper = new ObjectMapper();
        TypeReference<HashMap<Object, String>> typeRef = new TypeReference<HashMap<Object, String>>() {};
        
        int port = node.get("port").asInt();
        HashMap<Object, String> pages = mapper.convertValue(node.get("pages"), typeRef);
        HashMap<Object, String> ContentType = mapper.convertValue(node.get("ContentType"), typeRef);
 
        return new Config(port, pages, ContentType);
    }
}