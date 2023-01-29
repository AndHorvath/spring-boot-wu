package hu.webuni.hr.ah.model.base;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class PageResult<T> {

    // --- attributes ---------------------------------------------------------

    private List<T> dataObjects;
    private Map<String, Sort.Direction> ordering;
    private int pageSize;
    private int numberOfPages;
    private int actualPage;

    // --- constructors -------------------------------------------------------

    public PageResult() { }

    public PageResult(List<T> dataObjects, Map<String, Sort.Direction> ordering,
                      int pageSize, int numberOfPages, int actualPage) {
        this.dataObjects = dataObjects;
        this.ordering = ordering;
        this.pageSize = pageSize;
        this.numberOfPages = numberOfPages;
        this.actualPage = actualPage;
    }

    public PageResult(Pageable pageable, Page<T> page) {
        createPageResult(pageable, page);
    }

    // --- getters and setters ------------------------------------------------

    public List<T> getDataObjects() { return dataObjects; }
    public Map<String, Sort.Direction> getOrdering() { return ordering; }
    public int getPageSize() { return pageSize; }
    public int getNumberOfPages() { return numberOfPages; }
    public int getActualPage() { return actualPage; }

    public void setDataObjects(List<T> dataObjects) { this.dataObjects = dataObjects; }
    public void setOrdering(Map<String, Sort.Direction> ordering) { this.ordering = ordering; }
    public void setPageSize(int pageSize) { this.pageSize = pageSize; }
    public void setNumberOfPages(int numberOfPages) { this.numberOfPages = numberOfPages; }
    public void setActualPage(int actualPage) { this.actualPage = actualPage; }

    // --- private methods ----------------------------------------------------

    private void createPageResult(Pageable pageable, Page<T> page) {
        dataObjects = page.getContent();
        pageSize = pageable.getPageSize();
        numberOfPages = (int) Math.ceil(page.getTotalElements() / (double) pageSize);
        actualPage = pageable.getPageNumber() + 1;

        ordering = new LinkedHashMap<>();
        pageable.getSort().stream().forEach(order -> ordering.put(order.getProperty(), order.getDirection()));
    }
}