package hu.webuni.hr.ah.dto;

import com.fasterxml.jackson.annotation.JsonView;
import hu.webuni.hr.ah.view.CompanyTypeDataView;

public class CompanyTypeDto {

    // --- attributes ---------------------------------------------------------

    @JsonView(CompanyTypeDataView.IdentifierView.class)
    private final long id;

    @JsonView(CompanyTypeDataView.IdentifierView.class)
    private String name;

    // --- constructors -------------------------------------------------------

    public CompanyTypeDto(long id, String name) {
        this.id = id;
        this.name = name;
    }

    // --- getters and setters ------------------------------------------------

    public long getId() { return id; }
    public String getName() { return name; }
}