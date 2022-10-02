package bg.softuni.paintShop.model.validation;

import org.springframework.beans.BeanWrapper;
import org.springframework.beans.PropertyAccessorFactory;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;


public class PasswordMatchValidator implements ConstraintValidator<PasswordMatch, Object> {

    private String field;
    private String fieldMatch;
    private String message;

    @Override
    public void initialize(PasswordMatch constraintAnnotation) {

        this.field = constraintAnnotation.field();
        this.fieldMatch = constraintAnnotation.fieldMatch();
        this.message = constraintAnnotation.message();

    }

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext context) {
        BeanWrapper beanWrapper = PropertyAccessorFactory.
                forBeanPropertyAccess(value);

        Object firstValue = beanWrapper.getPropertyValue(this.field);
        Object secondValue = beanWrapper.getPropertyValue(this.fieldMatch);

        boolean valid;

        if (firstValue == null) {
            valid = secondValue == null;
        } else {
            valid = firstValue.equals(secondValue);
        }

        if (!valid) {
            context.
                    buildConstraintViolationWithTemplate(message).
                    addPropertyNode(fieldMatch).
                    addConstraintViolation().
                    disableDefaultConstraintViolation();
        }

        return valid;
    }

}
