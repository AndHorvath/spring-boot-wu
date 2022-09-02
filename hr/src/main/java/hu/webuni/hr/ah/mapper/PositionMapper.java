package hu.webuni.hr.ah.mapper;

import hu.webuni.hr.ah.dto.PageResultDto;
import hu.webuni.hr.ah.dto.PositionDto;
import hu.webuni.hr.ah.model.PageResult;
import hu.webuni.hr.ah.model.Position;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface PositionMapper {

    PositionDto positionToDto(Position position);
    Position dtoToPosition(PositionDto positionDto);

    List<PositionDto> positionsToDtos(List<Position> positions);

    PageResultDto<PositionDto> pageResultToDto(PageResult<Position> pageResult);
}