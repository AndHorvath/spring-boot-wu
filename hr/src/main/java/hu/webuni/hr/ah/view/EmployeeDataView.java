package hu.webuni.hr.ah.view;

public class EmployeeDataView {

    public interface IdentifierView { }
    public interface BaseDataView extends IdentifierView { }
    public interface DetailedDataView extends BaseDataView, CompanyDataView.IdentifierView { }
    public interface CompleteDataView extends DetailedDataView, CompanyDataView.BaseDataView { }
}