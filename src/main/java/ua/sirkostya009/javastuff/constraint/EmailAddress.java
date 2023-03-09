package ua.sirkostya009.javastuff.constraint;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = EmailValidator.class)
@Target({ElementType.TYPE_USE, ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface EmailAddress {
    String message() default "Email address invalid";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
