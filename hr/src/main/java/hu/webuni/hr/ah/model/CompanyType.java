package hu.webuni.hr.ah.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class CompanyType {

    // --- attributes ---------------------------------------------------------

    @Id
    @GeneratedValue
    private long id;

    private String name;

    // --- constructors -------------------------------------------------------

    public CompanyType() { }

    public CompanyType(String name) {
        this.name = name;
    }

    public CompanyType(long id, String name) {
        this.id = id;
        this.name = name;
    }

    // --- getters and setters ------------------------------------------------

    public long getId() { return id; }
    public String getName() { return name; }

    public void setId(long id) { this.id = id; }
    public void setName(String name) { this.name = name; }

    // --- public methods -----------------------------------------------------

    public CompanyType createCopyWithId(long id) {
        return new CompanyType(
            id,
            this.name
        );
    }
}