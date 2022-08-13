package hu.webuni.hr.ah.validation;

public class NonExistingIdentifierException extends InvalidIdentifierException {

    public NonExistingIdentifierException(long id) {
        super("No entry with specified ID in database: " + id);
    }
}