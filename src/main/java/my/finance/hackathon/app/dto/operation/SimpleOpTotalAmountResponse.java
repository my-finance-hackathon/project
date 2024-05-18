package my.finance.hackathon.app.dto.operation;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
@Builder
public class SimpleOpTotalAmountResponse {

    private BigDecimal totalAmount;

    List<ISimpleOpResponse> operations;
}
