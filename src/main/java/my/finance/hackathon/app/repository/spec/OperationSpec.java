package my.finance.hackathon.app.repository.spec;

import my.finance.hackathon.app.dto.operation.FilterDto;
import my.finance.hackathon.app.model.Operation;
import org.springframework.data.jpa.domain.Specification;

public interface OperationSpec {

    static Specification<Operation> withFilter(FilterDto filter) {
        return Specification.where(null);
    }
}
