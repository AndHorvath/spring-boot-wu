package hu.webuni.hr.ah.dto;

import com.fasterxml.jackson.annotation.JsonView;
import hu.webuni.hr.ah.view.PageDataView;
import org.springframework.data.domain.Sort;

import java.util.List;
import java.util.Map;

public class PageResultDto<T> {

    // --- attributes ---------------------------------------------------------

    @JsonView(PageDataView.BaseDataView.class)
    private List<T> dataObjects;

    @JsonView(PageDataView.CompleteDataView.class)
    private Map<String, Sort.Direction> ordering;

    @JsonView(PageDataView.CompleteDataView.class)
    private int pageSize;

    @JsonView(PageDataView.CompleteDataView.class)
    private int numberOfPages;

    @JsonView(PageDataView.BaseDataView.class)
    private int actualPage;

    // --- constructors -------------------------------------------------------

    public PageResultDto(List<T> dataObjects, Map<String, Sort.Direction> ordering,
                         int pageSize, int numberOfPages, int actualPage) {
        this.dataObjects = dataObjects;
        this.ordering = ordering;
        this.pageSize = pageSize;
        this.numberOfPages = numberOfPages;
        this.actualPage = actualPage;
    }

    // --- getters and setters ------------------------------------------------

    public List<T> getDataObjects() { return dataObjects; }
    public Map<String, Sort.Direction> getOrdering() { return ordering; }
    public int getPageSize() { return pageSize; }
    public int getNumberOfPages() { return numberOfPages; }
    public int getActualPage() { return actualPage; }
}