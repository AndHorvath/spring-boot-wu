package hu.webuni.hr.ah.view.base;

import hu.webuni.hr.ah.view.CompanyDataView;
import hu.webuni.hr.ah.view.CompanyTypeDataView;
import hu.webuni.hr.ah.view.EmployeeDataView;
import hu.webuni.hr.ah.view.PositionDataView;

public class PageDataView {

    public interface BaseDataView { }
    public interface CompleteDataView extends BaseDataView { }

    public interface PositionBaseDataView extends BaseDataView, PositionDataView.CompleteDataView { }
    public interface PositionCompleteDataView extends CompleteDataView, PositionBaseDataView { }

    public interface EmployeeBaseDataView extends BaseDataView, EmployeeDataView.DetailedDataView { }
    public interface EmployeeCompleteDataView extends CompleteDataView, EmployeeBaseDataView { }

    public interface CompanyTypeBaseDataView extends BaseDataView, CompanyTypeDataView.CompleteDataView { }
    public interface CompanyTypeCompleteDataView extends CompleteDataView, CompanyTypeBaseDataView { }

    public interface CompanyBaseDataView extends BaseDataView, CompanyDataView.DetailedDataView { }
    public interface CompanyCompleteDataView extends CompleteDataView, CompanyBaseDataView { }
}