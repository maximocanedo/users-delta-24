package ar.com.colectiva.validators.main.users;

import ar.com.colectiva.validators.main.commons.builders.StringValidatorBuilder;

public abstract class PasswordValidatorBuilder<T extends StringValidatorBuilder<T>> extends StringValidatorBuilder<T> {

    public PasswordValidatorBuilder(String username, String fieldName) {
        super(username, fieldName);
    }
    public PasswordValidatorBuilder(String username) {
        this(username, "username");
    }

    public T meetsLimits(String lowerLimitReachedCaseFeedback, String upperLimitReachedCaseFeedback) {
        return this
                .hasMinimumLength(8, lowerLimitReachedCaseFeedback)
                .hasMaximumLength(48, upperLimitReachedCaseFeedback);
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

    public T meetsContentRequirements(
            String doesNotHaveUppercasesCaseFeedback,
            String doesNotHaveLowercasesCaseFeedback,
            String doesNotHaveDigitsCaseFeedback,
            String doesNotHaveSpecialCharsCaseFeedback
    ) {
        return this
                .matches(".*[A-ZÑÇ].*", doesNotHaveUppercasesCaseFeedback)
                .matches(".*[a-zñç].*", doesNotHaveLowercasesCaseFeedback)
                .matches(".*\\d.*", doesNotHaveDigitsCaseFeedback)
                .matches(".*[^A-Za-z0-9].*", doesNotHaveSpecialCharsCaseFeedback);
    }
    public T meetsContentRequirements(String feedback) {
        return this.meetsContentRequirements(feedback, feedback, feedback, feedback);
    }
    public T meetsContentRequirements() {
        return this.meetsContentRequirements(
            "Debe tener al menos una mayúscula. ",
            "Debe tener al menos una minúscula. ",
            "Debe tener al menos un dígito. ",
            "Debe tener al menos un caracter especial. "
        );
    }


}
