package my.finance.hackathon.app.dto.operation;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.FieldNameConstants;
import my.finance.hackathon.app.model.Account;
import my.finance.hackathon.app.model.OperationType;

import java.math.BigDecimal;

@Data
@Builder
@FieldNameConstants
public class CreateOperationTransferRequest implements IUpsertOperationRequest {

    private Long accountFromId;

    private Long accountToId;

    private BigDecimal amount;

    @JsonIgnore
    private Account accountFrom;

    @JsonIgnore
    private Account accountTo;

    @Override
    public OperationType getType() {
        return OperationType.TRANSFER;
    }
}
