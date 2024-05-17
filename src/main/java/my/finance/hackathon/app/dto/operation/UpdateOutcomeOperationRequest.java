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
public class UpdateOutcomeOperationRequest implements UpsertOperationRequest {

    private Long accountFromId;

    private Category category;

    private BigDecimal amount;

    @JsonIgnore
    private Account accountFrom;

    @Override
    public OperationType getType() {
        return OperationType.OUTCOME;
    }
}
