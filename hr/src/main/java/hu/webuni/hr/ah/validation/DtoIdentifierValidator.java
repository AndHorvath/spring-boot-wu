package hu.webuni.hr.ah.validation;

import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class DtoIdentifierValidator {

    public void validateDtoIdentifierExistence(Map<Long, ?> dtoMap, long id) {
        if (!dtoMap.containsKey(id)) {
            throw new NonExistingIdentifierException(id);
        }
    }
}