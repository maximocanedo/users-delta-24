package com.max.users.validators.converters;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.max.users.validators.exceptions.ValidationError;

import java.io.IOException;

public class ValidationErrorSerializer extends JsonSerializer<ValidationError> {
    @Override
    public void serialize(ValidationError value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
        gen.writeStartObject();
        gen.writeStringField("field", value.getField());
        gen.writeArrayFieldStart("errors");
        for (String error : value.getErrors()) {
            gen.writeString(error);
        }
        gen.writeEndArray();
        gen.writeEndObject();
    }
}