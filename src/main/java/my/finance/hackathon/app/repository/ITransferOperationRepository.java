package my.finance.hackathon.app.repository;

import my.finance.hackathon.app.model.Operation;
import my.finance.hackathon.app.model.OperationType;
import my.finance.hackathon.app.model.TransferOperation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ITransferOperationRepository extends JpaRepository<TransferOperation, Long>, JpaSpecificationExecutor<TransferOperation> {

    Page<TransferOperation> findAllByType(OperationType type, Pageable pageable);

    List<TransferOperation> findAllByType(OperationType type);
}
