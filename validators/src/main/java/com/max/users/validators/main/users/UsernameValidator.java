package com.max.users.validators.main.users;

import com.max.users.validators.main.commons.builders.StringValidatorBuilder;

/**
 * <p>Clase validadora de cadenas de texto.</p>
 * <b>No heredar de esta clase, heredar de {@link StringValidatorBuilder StringValidatorBuilder}</b>
 */
public final class UsernameValidator extends UsernameValidatorBuilder<UsernameValidator> {

    public UsernameValidator(String value, String fieldName) {
        super(value, fieldName);
    }

    public UsernameValidator(String value) {
        this(value, "username");
    }

    /**
     * No añadir métodos a esta clase.
     */

}
