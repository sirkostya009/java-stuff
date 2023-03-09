package ua.sirkostya009.javastuff.constraint;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.regex.Pattern;

public class EmailValidator implements ConstraintValidator<EmailAddress, String> {
    private final static Pattern EMAIL_PATTERN = Pattern.compile("^(?=.{1,64}@)[\\p{L}0-9_-]+(\\.[\\p{L}0-9_-]+)*@[^-][\\p{L}0-9-]+(\\.[\\p{L}0-9-]+)*(\\.[\\p{L}]{2,})$");

    @Override
    public boolean isValid(String address, ConstraintValidatorContext constraintValidatorContext) {
        return EMAIL_PATTERN.matcher(address).matches();
    }
}
