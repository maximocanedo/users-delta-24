package ar.com.colectiva.converters;

import ar.com.colectiva.models.exceptions.Fail;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;

import java.io.IOException;

public class FailDeserializer extends JsonDeserializer<Fail> {
    @Override
    public Fail deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
        JsonNode node = p.getCodec().readTree(p);
        String message = node.get("message").asText();
        return new Fail(message);
    }
}