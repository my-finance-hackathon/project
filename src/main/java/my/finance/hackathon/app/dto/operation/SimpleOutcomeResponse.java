package my.finance.hackathon.app.dto.operation;

import lombok.Builder;
import lombok.Data;
import lombok.experimental.FieldNameConstants;
import my.finance.hackathon.app.dto.ShortAccountDto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
@FieldNameConstants
public class SimpleOutcomeResponse {

    private Long id;

    private ShortAccountDto accountFrom;

    private String category;

    private BigDecimal amount;

    private LocalDateTime dateTime;
}
