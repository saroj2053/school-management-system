package com.sah.bright_future_academy.validations;

import com.sah.bright_future_academy.annotation.FieldsValueMatch;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.beans.BeanWrapperImpl;

public class FieldsValueMatchValidator implements ConstraintValidator<FieldsValueMatch, Object> {

    private String field;
    private String fieldMatch;

    @Override
    public void initialize(FieldsValueMatch constraintAnnotation) {
        this.field = constraintAnnotation.field();
        this.fieldMatch = constraintAnnotation.fieldMatch();
    }

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext constraintValidatorContext) {
        Object fieldValue = new BeanWrapperImpl(value).getPropertyValue(field);
        Object fieldMatchValue = new BeanWrapperImpl(value).getPropertyValue(fieldMatch);

//        if (fieldValue != null) {
//            if (fieldValue.toString().startsWith("$2a")) {
//                return true;
//            } else {
//                return fieldValue.equals(fieldMatchValue);
//            }
//        } else {
//            return fieldMatchValue == null;
//        }

        if(fieldValue != null ) {
            return fieldValue.equals(fieldMatchValue);
        }
        else {
            return fieldMatchValue == null;
        }
    }
}
