package hu.webuni.hr.ah.validation;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import static org.assertj.core.api.Assertions.*;

class DataObjectIdentifierValidatorTest {

    DataObjectIdentifierValidator validator;

    Map<Long, Object> dataObject;
    Object value;
    Random random;

    @BeforeEach
    void setUp() {
        validator = new DataObjectIdentifierValidator();

        dataObject = new HashMap<>();
        value = new Object();
        random = new Random();

        dataObject.put(random.nextLong(10), value);
        dataObject.put(random.nextLong(10), value);
        dataObject.put(random.nextLong(10), value);
        dataObject.put(random.nextLong(10), value);
    }

    @Test
    void testValidateIdentifierExistence() {
        assertThatThrownBy(() -> validator.validateIdentifierExistence(dataObject, 10))
            .isInstanceOf(NonExistingIdentifierException.class)
            .hasMessage("No entry with specified ID in database: 10");
    }
}