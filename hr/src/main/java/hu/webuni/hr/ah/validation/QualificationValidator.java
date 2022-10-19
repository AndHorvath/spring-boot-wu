package hu.webuni.hr.ah.validation;

import hu.webuni.hr.ah.model.base.Qualification;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Arrays;

public class QualificationValidator implements ConstraintValidator<QualificationSubset, Qualification> {

    // --- attributes ---------------------------------------------------------

    private Qualification[] validSubset;

    // --- public methods -----------------------------------------------------

    @Override
    public void initialize(QualificationSubset constraintAnnotation) {
        validSubset = constraintAnnotation.anyOf();
    }

    @Override
    public boolean isValid(Qualification value, ConstraintValidatorContext context) {
        return value == null || Arrays.asList(validSubset).contains(value);
    }
}