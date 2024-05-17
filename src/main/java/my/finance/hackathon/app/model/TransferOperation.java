package my.finance.hackathon.app.model;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.FieldNameConstants;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "operation",
        indexes = {@Index(name = "date_index", columnList = "date_time"),
                @Index(name = "account_from_index", columnList = "account_from_id"),
                @Index(name = "account_to_index", columnList = "account_to_id")})
@Builder
@FieldNameConstants
public class TransferOperation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "account_from_id", nullable = false)
    private Account accountFrom;

    @ManyToOne
    @JoinColumn(name = "account_to_id", nullable = false)
    private Account accountTo;

    @Column(nullable = false, columnDefinition = "DECIMAL(10,2)")
    private BigDecimal amount;

    @CreationTimestamp
    @Column(name = "creation_date", nullable = false)
    private LocalDateTime creationDate;

    @UpdateTimestamp
    @Column(name = "update_date", nullable = false)
    private LocalDateTime updateDate;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, columnDefinition = "type character varying CHECK (type IN ('TRANSFER'))")
    private OperationType type;
}
