package hu.webuni.hr.ah.web;

import com.fasterxml.jackson.annotation.JsonView;
import hu.webuni.hr.ah.dto.EmployeeDto;
import hu.webuni.hr.ah.dto.base.PageResultDto;
import hu.webuni.hr.ah.dto.PositionDto;
import hu.webuni.hr.ah.mapper.EmployeeMapper;
import hu.webuni.hr.ah.mapper.PositionMapper;
import hu.webuni.hr.ah.service.PositionService;
import hu.webuni.hr.ah.view.EmployeeDataView;
import hu.webuni.hr.ah.view.base.PageDataView;
import hu.webuni.hr.ah.view.PositionDataView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/positions")
public class PositionController {

    // --- attributes ---------------------------------------------------------

    @Autowired
    private PositionService positionService;

    @Autowired
    private PositionMapper positionMapper;

    @Autowired
    private EmployeeMapper employeeMapper;

    // --- public methods -----------------------------------------------------

    @GetMapping
    @JsonView(PositionDataView.BaseDataView.class)
    public List<PositionDto> getPositions() {
        return positionMapper.positionsToDtos(positionService.getPositions());
    }

    @GetMapping(value = "/ordered", params = { "propertyName", "isAscending" })
    @JsonView(PositionDataView.CompleteDataView.class)
    public List<PositionDto> getPositionsOrderedByProperty(@RequestParam String propertyName,
                                                           @RequestParam boolean isAscending) {
        return positionMapper.positionsToDtos(positionService.getPositionsOrderedByProperty(propertyName, isAscending));
    }

    @GetMapping("/paginated")
    @JsonView(PageDataView.PositionCompleteDataView.class)
    public PageResultDto<PositionDto> getPositionsWithPagination(@PageableDefault(sort = "id") Pageable pageable) {
        return positionMapper.pageResultToDto(positionService.getPositionsWithPagination(pageable));
    }

    @GetMapping("/{id}")
    @JsonView(PositionDataView.CompleteDataView.class)
    public PositionDto getPositionById(@PathVariable long id) {
        return positionMapper.positionToDto(positionService.getPositionById(id));
    }

    @GetMapping(params = "positionNamePart")
    @JsonView(PositionDataView.CompleteDataView.class)
    public List<PositionDto> getPositionsByName(@RequestParam String positionNamePart) {
        return positionMapper.positionsToDtos(positionService.getPositionsByName(positionNamePart));
    }

    @GetMapping(params = "requiredQualification")
    @JsonView(PositionDataView.CompleteDataView.class)
    public List<PositionDto> getPositionsByRequiredQualification(@RequestParam String requiredQualification) {
        return positionMapper.positionsToDtos(
            positionService.getPositionsByRequiredQualification(requiredQualification)
        );
    }

    @GetMapping(params = "minimumSalary")
    @JsonView(PositionDataView.CompleteDataView.class)
    public List<PositionDto> getPositionsByMinimumSalary(@RequestParam int minimumSalary) {
        return positionMapper.positionsToDtos(positionService.getPositionsByMinimumSalary(minimumSalary));
    }

    @GetMapping("/company/{companyId}")
    @JsonView(PositionDataView.CompleteDataView.class)
    public List<PositionDto> getPositionsByCompany(@PathVariable long companyId) {
        return positionMapper.positionsToDtos(positionService.getPositionsByCompany(companyId));
    }

    @GetMapping("/employee/{positionId}")
    @JsonView(EmployeeDataView.CompletePositionDataView.class)
    public List<EmployeeDto> getEmployeesByPosition(@PathVariable long positionId) {
        return employeeMapper.employeesToDtos(positionService.getEmployeesByPosition(positionId));
    }

    @GetMapping(value = "/employee", params = "positionNamePart")
    @JsonView(EmployeeDataView.CompletePositionDataView.class)
    public List<EmployeeDto> getEmployeesByPositionName(@RequestParam String positionNamePart) {
        return employeeMapper.employeesToDtos(positionService.getEmployeesByPositionName(positionNamePart));
    }

    @GetMapping("/test")
    @JsonView(PositionDataView.CompleteDataView.class)
    public List<PositionDto> getTestData() {
        return positionMapper.positionsToDtos(positionService.setTestData());
    }

    @PostMapping
    @JsonView(PositionDataView.CompleteDataView.class)
    public PositionDto addPosition(@RequestBody @Valid PositionDto positionDto) {
        return positionMapper.positionToDto(positionService.savePosition(positionMapper.dtoToPosition(positionDto)));
    }

    @PutMapping("/{id}")
    @JsonView(PositionDataView.CompleteDataView.class)
    public PositionDto updatePosition(@PathVariable long id, @RequestBody @Valid PositionDto positionDto) {
        return positionMapper.positionToDto(
            positionService.updatePosition(id, positionMapper.dtoToPosition(positionDto))
        );
    }

    @DeleteMapping
    public void deletePositions() {
        positionService.deletePositions();
    }

    @DeleteMapping("/{id}")
    public void deletePositionById(@PathVariable long id) {
        positionService.deletePositionById(id);
    }
}