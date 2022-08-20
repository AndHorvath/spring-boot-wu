package hu.webuni.hr.ah.view;

public class CompanyDataView {

    public interface IdentifierView { }
    public interface BaseDataView extends IdentifierView, CompanyTypeDataView.IdentifierView { }
    public interface DetailedDataView extends BaseDataView, EmployeeDataView.IdentifierView { }
    public interface CompleteDataView extends DetailedDataView, EmployeeDataView.BaseDataView { }
}