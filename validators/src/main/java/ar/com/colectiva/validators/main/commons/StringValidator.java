package ar.com.colectiva.validators.main.commons;

import ar.com.colectiva.validators.main.commons.builders.StringValidatorBuilder;

/**
 * <p>Clase validadora de cadenas de texto.</p>
 * <b>No heredar de esta clase, heredar de {@link StringValidatorBuilder StringValidatorBuilder}</b>
 */
public final class StringValidator extends StringValidatorBuilder<StringValidator> {

    public StringValidator(String value, String fieldName) {
        super(value, fieldName);
    }

    public StringValidator(String value) {
        this(value, "unknown");
    }

    /**
     * No añadir métodos a esta clase.
     */

}
