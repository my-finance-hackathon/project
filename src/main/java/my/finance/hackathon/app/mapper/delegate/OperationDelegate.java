package my.finance.hackathon.app.mapper.delegate;

import my.finance.hackathon.app.dto.ShortAccountDto;
import my.finance.hackathon.app.dto.operation.*;
import my.finance.hackathon.app.mapper.IOperationMapper;
import my.finance.hackathon.app.model.Account;
import my.finance.hackathon.app.model.Operation;
import my.finance.hackathon.app.model.OperationType;
import my.finance.hackathon.app.model.TransferOperation;

import java.time.LocalDateTime;

public abstract class OperationDelegate implements IOperationMapper {

    @Override
    public Operation incomeRequestToOperation(CreateOperationIncomeRequest income) {
        return Operation.builder()
                .account(income.getAccountTo())
                .amount(income.getAmount())
                .category(income.getCategory())
                .type(income.getType())
                .build();
    }

    @Override
    public Operation outcomeRequestToOperation(CreateOperationOutcomeRequest outcome) {
        return Operation.builder()
                .account(outcome.getAccountFrom())
                .amount(outcome.getAmount())
                .category(outcome.getCategory())
                .type(outcome.getType())
                .build();
    }

    @Override
    public TransferOperation transferRequestToTransferOperation(CreateOperationTransferRequest transfer) {
        return TransferOperation.builder()
                .accountFrom(transfer.getAccountFrom())
                .accountTo(transfer.getAccountTo())
                .type(transfer.getType())
                .amount(transfer.getAmount())
                .build();
    }

    @Override
    public OperationResponseDto operationToResponse(Operation operation) {
        OperationType type = operation.getType();
        Account accountFrom = type.equals(OperationType.OUTCOME) ? operation.getAccount() : null;
        Account accountTo = type.equals(OperationType.INCOME) ? operation.getAccount() : null;
        return OperationResponseDto.builder()
                .id(operation.getId())
                .accountFrom(accountFrom != null ? ShortAccountDto.builder().id(accountFrom.getId()).name(accountFrom.getName()).build() : null)
                .accountTo(accountTo != null ? ShortAccountDto.builder().id(accountTo.getId()).name(accountTo.getName()).build() : null)
                .dateTime(prepareDateTimeForResponse(operation.getUpdateDate(), operation.getCreationDate()))
                .category(operation.getCategory())
                .type(operation.getType())
                .build();
    }

    @Override
    public OperationResponseDto transferToResponse(TransferOperation transferOperation) {
        Account accountFrom = transferOperation.getAccountFrom();
        Account accountTo = transferOperation.getAccountTo();
        return OperationResponseDto.builder()
                .id(transferOperation.getId())
                .accountFrom(ShortAccountDto.builder().id(accountFrom.getId()).name(accountFrom.getName()).build())
                .accountTo(ShortAccountDto.builder().id(accountTo.getId()).name(accountTo.getName()).build())
                .dateTime(prepareDateTimeForResponse(transferOperation.getUpdateDate(), transferOperation.getCreationDate()))
                .type(transferOperation.getType())
                .build();
    }

    @Override
    public SimpleIncomeResponse incomeToSimpleResponse(Operation operation) {
        Account accountTo = operation.getAccount();
        return SimpleIncomeResponse.builder()
                .id(operation.getId())
                .accountTo(ShortAccountDto.builder().id(accountTo.getId()).name(accountTo.getName()).build())
                .dateTime(prepareDateTimeForResponse(operation.getUpdateDate(), operation.getCreationDate()))
                .category(operation.getCategory().getName())
                .build();
    }

    @Override
    public SimpleOutcomeResponse outcomeToSimpleResponse(Operation operation) {
        Account accountFrom = operation.getAccount();
        return SimpleOutcomeResponse.builder()
                .id(operation.getId())
                .accountFrom(ShortAccountDto.builder().id(accountFrom.getId()).name(accountFrom.getName()).build())
                .dateTime(prepareDateTimeForResponse(operation.getUpdateDate(), operation.getCreationDate()))
                .category(operation.getCategory().getName())
                .build();
    }

    @Override
    public SimpleTransferResponse transferToSimpleResponse(TransferOperation transferOperation) {
        Account accountFrom = transferOperation.getAccountFrom();
        Account accountTo = transferOperation.getAccountTo();
        return SimpleTransferResponse.builder()
                .id(transferOperation.getId())
                .accountFrom(ShortAccountDto.builder().id(accountFrom.getId()).name(accountFrom.getName()).build())
                .accountFrom(ShortAccountDto.builder().id(accountTo.getId()).name(accountTo.getName()).build())
                .dateTime(prepareDateTimeForResponse(transferOperation.getUpdateDate(), transferOperation.getCreationDate()))
                .build();
    }

    @Override
    public Operation incomeRequestToOperation(UpdateIncomeOperationRequest income) {
        return null;
    }

    @Override
    public Operation outcomeRequestToOperation(UpdateOutcomeOperationRequest outcome) {
        return null;
    }

    @Override
    public TransferOperation transferRequestToTransferOperation(UpdateTransferOperationRequest transfer) {
        return null;
    }

    private LocalDateTime prepareDateTimeForResponse(LocalDateTime update, LocalDateTime create) {
        return update != null && update.isAfter(create)? update : create;
    }
}
