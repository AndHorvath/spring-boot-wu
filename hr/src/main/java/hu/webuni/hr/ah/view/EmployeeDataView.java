package hu.webuni.hr.ah.view;

public class EmployeeDataView {

    public interface IdentifierView { }
    public interface BaseDataView extends IdentifierView, PositionDataView.BaseDataView { }
    public interface DetailedDataView extends BaseDataView, CompanyDataView.IdentifierView { }
    public interface CompletePositionDataView extends DetailedDataView, PositionDataView.CompleteDataView { }
    public interface CompleteDataView extends CompletePositionDataView, CompanyDataView.BaseDataView { }
}