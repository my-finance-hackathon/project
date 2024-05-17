package my.finance.hackathon.app.dto.operation;

import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.FieldNameConstants;
import my.finance.hackathon.app.dto.ShortAccountDto;
import my.finance.hackathon.app.model.Category;
import my.finance.hackathon.app.model.OperationType;
import org.hibernate.annotations.CreationTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
@FieldNameConstants
public class OperationResponseDto {

    private Long id;

    private ShortAccountDto accountFrom;

    private ShortAccountDto accountTo;

    private Category category;

    @Column(nullable = false, columnDefinition = "DECIMAL(10,2)")
    private BigDecimal amount;

    @CreationTimestamp
    @Column(name = "date_time", nullable = false)
    private LocalDateTime dateTime;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private OperationType type;
}
