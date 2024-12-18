package com.max.users.models.exceptions;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.max.users.converters.FailDeserializer;
import com.max.users.converters.FailSerializer;
import org.springframework.http.HttpStatus;

@JsonSerialize(using = FailSerializer.class)
@JsonDeserialize(using = FailDeserializer.class)
@JsonIgnoreProperties(ignoreUnknown = true)
public class Fail extends RuntimeException {

    private String message = "";
    private HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;

    public Fail(String message) {
        this(message, HttpStatus.INTERNAL_SERVER_ERROR);
    }
    public Fail(String message, HttpStatus status) {
        super(message);
        this.message = message;
        this.status = status;
    }
    public Fail() {
        this("Unknown error");
    }

    @JsonProperty
    public String getMessage() { return message; }

    @JsonIgnore
    public HttpStatus getStatus() { return status; }

}