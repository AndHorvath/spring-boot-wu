package hu.webuni.hr.ah.validation.identifier;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class DataObjectIdentifierValidator {

    public void validateIdentifierExistence(Map<Long, ?> dataObjectMap, long id) {
        if (!dataObjectMap.containsKey(id)) {
            throw new NonExistingIdentifierException(id);
        }
    }

    public void validateIdentifierExistence(JpaRepository<?, Long> repository, long id) {
        if (!repository.existsById(id)) {
            throw new NonExistingIdentifierException(id, repository);
        }
    }
}