package my.finance.hackathon.app.dto.operation;

import lombok.Builder;
import lombok.Data;
import lombok.experimental.FieldNameConstants;

@Data
@Builder
@FieldNameConstants
public class OperationResult {

    boolean result;

    String message;
}
