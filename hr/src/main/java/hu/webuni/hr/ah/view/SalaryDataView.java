package hu.webuni.hr.ah.view;

public class SalaryDataView {

    public interface EmployeeBaseDataView extends EmployeeDataView.BaseDataView { }
    public interface EmployeeCompleteDataView extends EmployeeBaseDataView, EmployeeDataView.CompleteDataView { }

    public interface CompanyBaseDataView extends CompanyDataView.IdentifierView, PositionDataView.CompleteDataView { }
    public interface CompanyCompleteDataView extends CompanyBaseDataView, CompanyDataView.BaseDataView { }
}