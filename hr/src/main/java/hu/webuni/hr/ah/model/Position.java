package hu.webuni.hr.ah.model;

import hu.webuni.hr.ah.model.base.Qualification;

import javax.persistence.*;

@Entity
public class Position {

    // --- attributes ---------------------------------------------------------

    @Id
    @GeneratedValue
    private long id;

    private String name;

    @Enumerated(EnumType.STRING)
    private Qualification requiredQualification;

    private int minimumSalary;

    // --- constructors -------------------------------------------------------

    public Position() { }

    public Position(String name, Qualification requiredQualification, int minimumSalary) {
        this.name = name;
        this.requiredQualification = requiredQualification;
        this.minimumSalary = minimumSalary;
    }

    public Position(long id, String name, Qualification requiredQualification, int minimumSalary) {
        this.id = id;
        this.name = name;
        this.requiredQualification = requiredQualification;
        this.minimumSalary = minimumSalary;
    }

    // --- getters and setters ------------------------------------------------

    public long getId() { return id; }
    public String getName() { return name; }
    public Qualification getRequiredQualification() { return requiredQualification; }
    public int getMinimumSalary() { return minimumSalary; }

    public void setId(long id) { this.id = id; }
    public void setName(String name) { this.name = name; }
    public void setRequiredQualification(Qualification requiredQualification) { this.requiredQualification = requiredQualification; }
    public void setMinimumSalary(int minimumSalary) { this.minimumSalary = minimumSalary; }

    // --- public methods -----------------------------------------------------

    public Position createCopyWithId(long id) {
        return new Position(
            id,
            this.name,
            this.requiredQualification,
            this.minimumSalary
        );
    }
}