package ar.com.colectiva.validators.exceptions;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import ar.com.colectiva.validators.converters.ValidationErrorDeserializer;
import ar.com.colectiva.validators.converters.ValidationErrorSerializer;

import java.util.Set;
import java.util.stream.Collectors;

@JsonSerialize(using = ValidationErrorSerializer.class)
@JsonDeserialize(using = ValidationErrorDeserializer.class)
@JsonIgnoreProperties(ignoreUnknown = true)
public class ValidationError extends RuntimeException {
    private final String field;
    private final Set<String> errors;

    public ValidationError(String field, Set<String> errors) {
        this.field = field;
        this.errors = errors;
    }

    @JsonProperty
    public String getField() {
        return field;
    }

    @JsonProperty
    public Set<String> getErrors() {
        return errors;
    }

    @JsonIgnore
    public String getMessage() {
        return new StringBuilder()
                .append("Validation error for field '")
                .append(getField())
                .append("' with errors:")
                .append(getErrors().stream()
                        .map(error -> "\n\t · " + error)
                        .collect(Collectors.joining("")))
                .toString();
    }

}
