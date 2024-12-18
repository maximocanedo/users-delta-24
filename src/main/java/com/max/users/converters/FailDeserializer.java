package com.max.users.converters;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.max.users.models.exceptions.Fail;

import java.io.IOException;

public class FailDeserializer extends JsonDeserializer<Fail> {
    @Override
    public Fail deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
        JsonNode node = p.getCodec().readTree(p);
        String message = node.get("message").asText();
        return new Fail(message);
    }
}