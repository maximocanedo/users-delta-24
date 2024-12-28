package ar.com.colectiva.validators.main.commons.builders;

import ar.com.colectiva.validators.exceptions.ValidationError;

import java.util.LinkedHashSet;
import java.util.Set;

public abstract class ValidatorBuilder<V extends ValidatorBuilder<V, T>, T> {

    public enum OnInvalidateAction {
        THROW_EXCEPTION,
        THROW_EXCEPTION_ON_BUILD,
        DO_NOTHING
    }

    public OnInvalidateAction onInvalidateAction = OnInvalidateAction.THROW_EXCEPTION;

    @SuppressWarnings("unchecked")
    public V onInvalidate(OnInvalidateAction action) {
        this.onInvalidateAction = action;
        return (V) this;
    }

    private T value;
    private boolean valid = true;
    private String fieldName = "";
    private final Set<String> errors = new LinkedHashSet<String>();

    public ValidatorBuilder(T value) {
        setValue(value);
    }
    public ValidatorBuilder(T value, String fieldName) {
        this(value);
        this.fieldName = fieldName;
    }

    protected void invalidate(String reason) {
        errors.add(reason);
        this.valid = false;
        if(onInvalidateAction.equals(OnInvalidateAction.THROW_EXCEPTION)) {
            throw new ValidationError(getField(), getErrors());
        }
    }

    public String getField() { return fieldName; }
    public Set<String> getErrors() { return errors; }
    public boolean isValid() { return valid; }
    protected T getValue() { return value; }
    protected void setValue(T newValue) {
        value = newValue;
    }
    public T build() {
        if(!isValid() && onInvalidateAction.equals(OnInvalidateAction.THROW_EXCEPTION_ON_BUILD)) {
            throw new ValidationError(getField(), getErrors());
        }
        return value;
    }

}