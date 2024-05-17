package my.finance.hackathon.app.dto;

import lombok.Builder;
import lombok.Data;
import lombok.experimental.FieldNameConstants;

@Data
@Builder
@FieldNameConstants
public class ShortAccountDto {

    private Long id;

    private String name;
}
