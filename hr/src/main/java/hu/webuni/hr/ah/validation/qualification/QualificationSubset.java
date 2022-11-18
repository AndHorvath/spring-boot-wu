package hu.webuni.hr.ah.validation.qualification;

import hu.webuni.hr.ah.model.base.Qualification;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.*;

@Documented
@Retention(RUNTIME)
@Constraint(validatedBy = QualificationValidator.class)
@Target({ METHOD, FIELD, ANNOTATION_TYPE, PARAMETER, TYPE_USE })
public @interface QualificationSubset {

    Qualification[] anyOf();

    String message() default "{validation.QualificationSubset.message}";
    Class<?>[] groups() default { };
    Class<? extends Payload>[] payload() default { };
}