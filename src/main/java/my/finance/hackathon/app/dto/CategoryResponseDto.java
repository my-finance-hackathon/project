package my.finance.hackathon.app.dto;

import lombok.Builder;
import lombok.Data;
import lombok.experimental.FieldNameConstants;
import my.finance.hackathon.app.model.OperationType;

@Data
@Builder
@FieldNameConstants
public class CategoryResponseDto {

    private Long id;

    private String name;

    private OperationType availableOperationType;
}
