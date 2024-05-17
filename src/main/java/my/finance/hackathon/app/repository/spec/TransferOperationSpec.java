package my.finance.hackathon.app.repository.spec;

import my.finance.hackathon.app.dto.operation.FilterDto;
import my.finance.hackathon.app.model.TransferOperation;
import org.springframework.data.jpa.domain.Specification;

public interface TransferOperationSpec {

    static Specification<TransferOperation> withFilter(FilterDto filter) {
        return Specification.where(null);
    }
}
