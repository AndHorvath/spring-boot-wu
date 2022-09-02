package hu.webuni.hr.ah.view;

public class SalaryDataView {

    public interface EmployeeBaseDataView extends EmployeeDataView.BaseDataView { }
    public interface EmployeeCompleteDataView extends EmployeeBaseDataView, EmployeeDataView.DetailedDataView { }

    public interface PositionBaseDataView extends PositionDataView.BaseDataView { }
    public interface PositionCompleteDataView extends PositionBaseDataView, PositionDataView.CompleteDataView { }

    public interface CompanyBaseDataView extends CompanyDataView.IdentifierView, PositionCompleteDataView { }
    public interface CompanyCompleteDataView extends CompanyBaseDataView, CompanyDataView.BaseDataView { }
}