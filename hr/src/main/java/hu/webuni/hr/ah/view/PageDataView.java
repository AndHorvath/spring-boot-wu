package hu.webuni.hr.ah.view;

public class PageDataView {

    public interface BaseDataView { }
    public interface CompleteDataView extends BaseDataView { }

    public interface EmployeeBaseDataView extends BaseDataView, EmployeeDataView.DetailedDataView { }
    public interface EmployeeCompleteDataView extends CompleteDataView, EmployeeBaseDataView { }

    public interface CompanyTypeBaseDataView extends BaseDataView, CompanyTypeDataView.CompleteDataView { }
    public interface CompanyTypeCompleteDataView extends CompleteDataView, CompanyTypeBaseDataView { }

    public interface CompanyBaseDataView extends BaseDataView, CompanyDataView.DetailedDataView { }
    public interface CompanyCompleteDataView extends CompleteDataView, CompanyBaseDataView { }
}