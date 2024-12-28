package ar.com.colectiva.validators.main.users;

import ar.com.colectiva.validators.main.commons.builders.StringValidatorBuilder;

/**
 * <p>Clase validadora de cadenas de texto.</p>
 * <b>No heredar de esta clase, heredar de {@link StringValidatorBuilder StringValidatorBuilder}</b>
 */
public final class PasswordValidator extends PasswordValidatorBuilder<PasswordValidator> {

    public PasswordValidator(String value, String fieldName) {
        super(value, fieldName);
    }

    public PasswordValidator(String value) {
        this(value, "password");
    }

    /**
     * No añadir métodos a esta clase.
     */

}
