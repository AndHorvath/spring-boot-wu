package hu.webuni.hr.ah.validation;

import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class DataObjectIdentifierValidator {

    public void validateIdentifierExistence(Map<Long, ?> dataObjectMap, long id) {
        if (!dataObjectMap.containsKey(id)) {
            throw new NonExistingIdentifierException(id);
        }
    }
}