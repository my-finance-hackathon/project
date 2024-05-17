package my.finance.hackathon.app.model;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.FieldNameConstants;

@Data
@Entity
@Table(name = "category",
        indexes = {@Index(name = "category_name", columnList = "name"),
                @Index(name = "category_operation_type", columnList = "available_operation_type_id")},
        uniqueConstraints = {@UniqueConstraint(name = "category_name_unique_key", columnNames = "name")})
@FieldNameConstants
@Builder
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, columnDefinition = "VARCHAR(255)")
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(name = "available_operation_type_id", nullable = false)
    private OperationType availableOperationType;
}
