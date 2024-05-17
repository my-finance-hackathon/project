package my.finance.hackathon.app.mapper;

import my.finance.hackathon.app.dto.CategoryResponseDto;
import my.finance.hackathon.app.dto.CategoryUpsertRequestDto;
import my.finance.hackathon.app.model.Category;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ICategoryMapper {

    Category upsertRequestToCategory(CategoryUpsertRequestDto request);

    CategoryResponseDto categoryToResponse(Category category);
}
