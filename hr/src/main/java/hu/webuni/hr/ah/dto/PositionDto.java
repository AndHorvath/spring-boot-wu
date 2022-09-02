package hu.webuni.hr.ah.dto;

import com.fasterxml.jackson.annotation.JsonView;
import hu.webuni.hr.ah.model.Qualification;
import hu.webuni.hr.ah.view.PositionDataView;

import javax.validation.constraints.Positive;

public class PositionDto {

    // --- attributes ---------------------------------------------------------

    @JsonView(PositionDataView.BaseDataView.class)
    private final long id;

    @JsonView(PositionDataView.BaseDataView.class)
    private String name;

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