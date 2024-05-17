package my.finance.hackathon.app.mapper;

import my.finance.hackathon.app.dto.operation.*;
import my.finance.hackathon.app.mapper.delegate.OperationDelegate;
import my.finance.hackathon.app.model.Operation;
import my.finance.hackathon.app.model.TransferOperation;
import org.mapstruct.DecoratedWith;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@DecoratedWith(OperationDelegate.class)
@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.WARN)
public interface IOperationMapper {

    Operation incomeRequestToOperation(CreateOperationIncomeRequest income);

    Operation outcomeRequestToOperation(CreateOperationOutcomeRequest outcome);

    TransferOperation transferRequestToTransferOperation(CreateOperationTransferRequest transfer);

    OperationResponseDto operationToResponse(Operation operation);

    OperationResponseDto transferToResponse(TransferOperation transferOperation);

    SimpleIncomeResponse incomeToSimpleResponse(Operation operation);

    SimpleOutcomeResponse outcomeToSimpleResponse(Operation operation);

    SimpleTransferResponse transferToSimpleResponse(TransferOperation transferOperation);
}
