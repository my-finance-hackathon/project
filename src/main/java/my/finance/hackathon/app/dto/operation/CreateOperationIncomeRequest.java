package my.finance.hackathon.app.dto.operation;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.FieldNameConstants;
import my.finance.hackathon.app.model.Account;
import my.finance.hackathon.app.model.Category;
import my.finance.hackathon.app.model.OperationType;

import java.math.BigDecimal;

@Data
@Builder
@FieldNameConstants
public class CreateOperationIncomeRequest implements IUpsertOperationRequest {

    private Long accountToId;

    private BigDecimal amount;

    private Long categoryId;

    @JsonIgnore
    private Account accountTo;

    @JsonIgnore
    private Category category;

    @Override
    public OperationType getType() {
        return OperationType.INCOME;
    }
}
