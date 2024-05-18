package my.finance.hackathon.app.repository;

import my.finance.hackathon.app.model.Category;
import my.finance.hackathon.app.model.Operation;
import my.finance.hackathon.app.model.OperationType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IOperationRepository extends JpaRepository<Operation, Long>, JpaSpecificationExecutor<Operation> {

    Page<Operation> findAllByType(OperationType type, Pageable pageable);

    List<Operation> findAllByType(OperationType type, Sort sort);

    List<Operation> findAllByCategory(Category category, Sort sort);

    Page<Operation> findAllByCategory(Category category, Pageable pageable);
}
