package hu.webuni.hr.ah.validation.identifier;

import org.springframework.data.jpa.repository.JpaRepository;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.stream.Stream;

public class NonExistingIdentifierException extends InvalidIdentifierException {

    public NonExistingIdentifierException(long id) {
        super("No entry with specified ID in database: " + id);
    }

    public NonExistingIdentifierException(long id, JpaRepository<?, Long> repository) {
        super("No " + getEntityName(repository) + " entry with specified ID in database: " + id);
    }

    // --- private methods ----------------------------------------------------

    private static String getEntityName(JpaRepository<?, Long> repository) {
        Type entityTypeArgument = ((ParameterizedType) ((Class<?>) repository
            .getClass().getGenericInterfaces()[0]).getGenericInterfaces()[0])
            .getActualTypeArguments()[0];
        String[] entityTypeArgumentNameArray = entityTypeArgument.getTypeName().split("\\.");
        return findLast(Stream.of(entityTypeArgumentNameArray));
    }

    private static String findLast(Stream<String> stringStream) {
        return stringStream.reduce("", (first, second) -> second);
    }
}