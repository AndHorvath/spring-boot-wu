package hu.webuni.hr.ah.dto;

import com.fasterxml.jackson.annotation.JsonView;
import hu.webuni.hr.ah.model.base.Qualification;
import hu.webuni.hr.ah.validation.QualificationSubset;
import hu.webuni.hr.ah.view.PositionDataView;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

public class PositionDto {

    // --- attributes ---------------------------------------------------------

    @JsonView(PositionDataView.IdentifierView.class)
    private final long id;

    @NotBlank
    @JsonView(PositionDataView.BaseDataView.class)
    private String name;

    @NotNull
    @QualificationSubset(anyOf = {
        Qualification.NONE,
        Qualification.ADVANCED_LEVEL, Qualification.COLLAGE,
        Qualification.UNIVERSITY, Qualification.PHD
    })
    @JsonView(PositionDataView.CompleteDataView.class)
    private Qualification requiredQualification;

    @Positive
    @JsonView(PositionDataView.CompleteDataView.class)
    private int minimumSalary;

    // --- constructors -------------------------------------------------------

    public PositionDto(long id, String name, Qualification requiredQualification, int minimumSalary) {
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
}