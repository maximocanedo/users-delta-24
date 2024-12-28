package ar.com.colectiva.validators.main.users;

import ar.com.colectiva.validators.main.commons.builders.StringValidatorBuilder;

public abstract class UsernameValidatorBuilder<T extends StringValidatorBuilder<T>> extends StringValidatorBuilder<T> {

    private UsernameValidatorRepository repository = null;

    public UsernameValidatorBuilder(String username, String fieldName) {
        super(username, fieldName);
    }
    public UsernameValidatorBuilder(String username) {
        this(username, "username");
    }

    public UsernameValidatorBuilder<T> useRepository(UsernameValidatorRepository repository) {
        this.repository = repository;
        return this;
    }
    public UsernameValidatorBuilder<T> doNotUseRepository() {
        this.repository = null;
        return this;
    }

    public T meetsLimits(String lowerLimitReachedCaseFeedback, String upperLimitReachedCaseFeedback) {
        return this
                .hasMinimumLength(3, lowerLimitReachedCaseFeedback)
                .hasMaximumLength(32, upperLimitReachedCaseFeedback);
    }
    public T meetsLimits(String bothCasesFeedback) {
        return this.meetsLimits(bothCasesFeedback, bothCasesFeedback);
    }
    public T meetsLimits() {
        return this.meetsLimits(
            "Debe contener al menos tres caracteres. ",
            "No puede tener más de 32 caracteres. "
        );
    }

    public T meetsContentRequirements(String doesNotStartWithLetterCaseFeedback, String generalCaseFeedback) {
        return this
                .matches("^[a-zA-Z].*", doesNotStartWithLetterCaseFeedback)
                .matches("^[a-zA-Z0-9_]+$", generalCaseFeedback);
    }
    public T meetsContentRequirements(String feedback) {
        return this.meetsContentRequirements(feedback, feedback);
    }
    public T meetsContentRequirements() {
        return this.meetsContentRequirements("Debe comenzar con una letra. ", "Debe tener únicamente letras, números y/o guiones bajos. ");
    }

    public UsernameValidatorBuilder<T> isAvailable(String feedback) {
        if(repository != null && repository.existsByUsername(this.getValue()))
            super.invalidate(feedback);
        return this;
    }
    public UsernameValidatorBuilder<T> isAvailable() {
        return this.isAvailable("Este nombre de usuario no está disponible. ");
    }

}
