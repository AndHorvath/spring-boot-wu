package hu.webuni.hr.ah.web;

import com.fasterxml.jackson.annotation.JsonView;
import hu.webuni.hr.ah.dto.PageResultDto;
import hu.webuni.hr.ah.dto.PositionDto;
import hu.webuni.hr.ah.mapper.PositionMapper;
import hu.webuni.hr.ah.service.PositionService;
import hu.webuni.hr.ah.view.PageDataView;
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

    // --- public methods -----------------------------------------------------

    @GetMapping
    @JsonView(PositionDataView.CompleteDataView.class)
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

    @GetMapping(params = "requiredQualification")
    @JsonView(PositionDataView.CompleteDataView.class)
    public List<PositionDto> getPositionsByRequiredQualification(@RequestParam String requiredQualification) {
        return positionMapper.positionsToDtos(positionService.getPositionsByRequiredQualification(requiredQualification));
    }

    @GetMapping(params = "minimumSalary")
    @JsonView(PositionDataView.CompleteDataView.class)
    public List<PositionDto> getPositionsByMinimumSalary(@RequestParam int minimumSalary) {
        return positionMapper.positionsToDtos(positionService.getPositionsByMinimumSalary(minimumSalary));
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
    public PositionDto updatePosition(@PathVariable long id, @RequestBody PositionDto positionDto) {
        return positionMapper.positionToDto(positionService.updatePosition(id, positionMapper.dtoToPosition(positionDto)));
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