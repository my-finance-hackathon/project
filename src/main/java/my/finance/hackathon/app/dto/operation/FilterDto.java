package my.finance.hackathon.app.dto.operation;

import lombok.Data;
import lombok.experimental.FieldNameConstants;
import my.finance.hackathon.app.model.OperationType;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
@FieldNameConstants
public class FilterDto {

    private List<Long> accountIds;

    private List<Long> categoryId;

    private BigDecimal minAmount;

    private BigDecimal maxAmount;

    private LocalDateTime dateFrom;

    private LocalDateTime dateTo;

    private List<OperationType> operationTypes;
}
