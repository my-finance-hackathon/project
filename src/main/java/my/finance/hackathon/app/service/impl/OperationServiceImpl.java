package my.finance.hackathon.app.service.impl;

import lombok.RequiredArgsConstructor;
import my.finance.hackathon.app.dto.operation.*;
import my.finance.hackathon.app.exceptions.NotFoundException;
import my.finance.hackathon.app.exceptions.OperationException;
import my.finance.hackathon.app.mapper.IOperationMapper;
import my.finance.hackathon.app.model.*;
import my.finance.hackathon.app.repository.IOperationRepository;
import my.finance.hackathon.app.repository.ITransferOperationRepository;
import my.finance.hackathon.app.repository.spec.OperationSpec;
import my.finance.hackathon.app.repository.spec.TransferOperationSpec;
import my.finance.hackathon.app.service.AccountService;
import my.finance.hackathon.app.service.ICategoryService;
import my.finance.hackathon.app.service.IOperationService;
import my.finance.hackathon.app.util.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
public class OperationServiceImpl implements IOperationService {

    private final IOperationMapper operationMapper;

    private final ITransferOperationRepository transferRepository;

    private final IOperationRepository operationRepository;

    private final ICategoryService categoryService;

    private final AccountService accountService;

    @Override
    public OperationResponseDto findByIdAndOperationType(Long id, OperationType type) {
        return null;
    }

    @Override
    public List<OperationResponseDto> findAll() {
        List<Operation> operations = operationRepository.findAll();
        List<TransferOperation> transfers = transferRepository.findAll();
        return Stream.concat(operations.stream().map(operationMapper::operationToResponse),
                transfers.stream().map(operationMapper::transferToResponse)).toList();
    }

    @Override
    public Page<OperationResponseDto> findAll(Pageable pageable) {

        Page<Operation> operations = operationRepository.findAll(pageable);
        Page<TransferOperation> transfers = transferRepository.findAll(pageable);
        List<OperationResponseDto> response = Stream.concat(
                operations.getContent().stream().map(operationMapper::operationToResponse),
                transfers.getContent().stream().map(operationMapper::transferToResponse)).toList();
        return new PageImpl<>(response, pageable, (operations.getTotalElements() + transfers.getTotalElements()));
    }

    @Override
    public List<OperationResponseDto> findAllByOperationType(OperationType type) {
        if (type.equals(OperationType.TRANSFER)) {
            List<TransferOperation> transfers = transferRepository.findAllByType(type);
            return transfers.stream().map(operationMapper::transferToResponse).toList();
        } else {
            List<Operation> transfers = operationRepository.findAllByType(type);
            return transfers.stream().map(operationMapper::operationToResponse).toList();
        }
    }

    @Override
    public Page<OperationResponseDto> findAllByOperationType(Pageable pageable, OperationType type) {
        if (type.equals(OperationType.TRANSFER)) {
            Page<TransferOperation> transfers = transferRepository.findAllByType(type, pageable);
            List<OperationResponseDto> response = transfers.getContent().stream().map(operationMapper::transferToResponse).toList();
            return new PageImpl<>(response, pageable, transfers.getTotalElements());
        } else {
            Page<Operation> operations = operationRepository.findAllByType(type, pageable);
            List<OperationResponseDto> response = operations.getContent().stream().map(operationMapper::operationToResponse).toList();
            return new PageImpl<>(response, pageable, operations.getTotalElements());
        }
    }

    @Override
    public List<OperationResponseDto> findStandardOpByCategory(Long categoryId) {
        Category category = categoryService.findEntityById(categoryId);
        List<Operation> operations = operationRepository.findAllByCategory(category);
        return operations.stream().map(operationMapper::operationToResponse).toList();
    }

    @Override
    public Page<OperationResponseDto> findStandardOpByCategory(Pageable pageable, Long categoryId) {
        Category category = categoryService.findEntityById(categoryId);
        Page<Operation> operations = operationRepository.findAllByCategory(category, pageable);
        List<OperationResponseDto> response = operations.getContent().stream().map(operationMapper::operationToResponse).toList();
        return new PageImpl<>(response, pageable, operations.getTotalElements());
    }

    @Override
    public List<OperationResponseDto> findAllWithFilter(FilterDto filter) {
        List<Operation> operations = operationRepository.findAll(OperationSpec.withFilter(filter));
        List<TransferOperation> transfers = transferRepository.findAll(TransferOperationSpec.withFilter(filter));
        return Stream.concat(
                operations.stream().map(operationMapper::operationToResponse),
                transfers.stream().map(operationMapper::transferToResponse)).toList();
    }

    @Override
    public Page<OperationResponseDto> findStandardOpWithFilter(Pageable pageable, FilterDto filter) {
        Page<Operation> operations = operationRepository.findAll(OperationSpec.withFilter(filter), pageable);
        Page<TransferOperation> transfers = transferRepository.findAll(TransferOperationSpec.withFilter(filter), pageable);
        List<OperationResponseDto> response = Stream.concat(
                operations.getContent().stream().map(operationMapper::operationToResponse),
                transfers.getContent().stream().map(operationMapper::transferToResponse)).toList();
        return new PageImpl<>(response, pageable, (operations.getTotalElements() + transfers.getTotalElements()));
    }

    /**
     * Base method to delegate the process to another ones
     */
    @Override
    public OperationResult makeTransferOp(IUpsertOperationRequest request) {
        if (request instanceof CreateOperationIncomeRequest && request.getType().equals(OperationType.INCOME)) {
            return createIncomingOperation((CreateOperationIncomeRequest) request);
        } else if (request instanceof CreateOperationOutcomeRequest && request.getType().equals(OperationType.OUTCOME)) {
            return createOutComingOperation((CreateOperationOutcomeRequest) request);
        } else if (request instanceof CreateOperationTransferRequest && request.getType().equals(OperationType.TRANSFER)) {
            return createTransferOperation((CreateOperationTransferRequest) request);
        } else {
            throw new OperationException("Incorrect input date");
        }
    }

    private OperationResult createIncomingOperation(CreateOperationIncomeRequest request) {
        Account accountTo = accountService.getById(request.getAccountToId());
        Category category = categoryService.findEntityById(request.getCategoryId());
        request.setAccountTo(accountTo);
        request.setCategory(category);
        try {
            accountService.plus(request.getAccountToId(), request.getAmount().doubleValue());
            Operation operation = operationMapper.incomeRequestToOperation(request);
            operation.setType(OperationType.INCOME);
            operation.setCreationDate(LocalDateTime.now());
            Long operationId = operationRepository.save(operation).getId();
            return OperationResult.builder().result(true).message(String.format("Operation created, №%d", operationId)).build();
        } catch (OperationException exception) {
            //ToDo: сделать логирование
            return OperationResult.builder().result(false).message("Operation declined").build();
        }
    }

    private OperationResult createOutComingOperation(CreateOperationOutcomeRequest request) {
        Account accountFrom = accountService.getById(request.getAccountFromId());
        Category category = categoryService.findEntityById(request.getCategoryId());
        request.setAccountFrom(accountFrom);
        request.setCategory(category);
        Operation operation = operationMapper.outcomeRequestToOperation(request);
        operation.setType(OperationType.OUTCOME);
        operation.setCreationDate(LocalDateTime.now());
        try {
            accountService.minus(request.getAccountFromId(), request.getAmount().doubleValue());
            operation.setSuccess(true);
            Long operationId = operationRepository.save(operation).getId();
            return OperationResult.builder().result(true).message(String.format("Operation created, №%d", operationId)).build();
        } catch (OperationException exception) {
            operation.setSuccess(true);
            Long operationId = operationRepository.save(operation).getId();
            return OperationResult.builder().result(false).message(String.format("Operation declined, №%d", operationId)).build();
        }
    }

    private OperationResult createTransferOperation(CreateOperationTransferRequest request) {
        Account accountTo = accountService.getById(request.getAccountToId());
        Account accountFrom = accountService.getById(request.getAccountFromId());
        request.setAccountTo(accountTo);
        request.setAccountFrom(accountFrom);
        try {
            accountService.minus(request.getAccountFromId(), request.getAmount().doubleValue());
            accountService.plus(request.getAccountToId(), request.getAmount().doubleValue());
            TransferOperation operation = operationMapper.transferRequestToTransferOperation(request);
            operation.setType(OperationType.TRANSFER);
            operation.setCreationDate(LocalDateTime.now());
            Long operationId = transferRepository.save(operation).getId();
            return OperationResult.builder().result(true).message(String.format("Transfer operation created, №%d", operationId)).build();
        } catch (OperationException exception) {
            //ToDo: сделать логирование
            return OperationResult.builder().result(false).message("Operation declined").build();
        }
    }

    /**
     * Base method to delegate the process to another ones
     */
    @Override
    public OperationResult updateById(Long id, IUpsertOperationRequest request) {
        if (request instanceof UpdateIncomeOperationRequest && request.getType().equals(OperationType.INCOME)) {
            return updateIncomeOperation(id, (UpdateIncomeOperationRequest) request);
        } else if (request instanceof UpdateOutcomeOperationRequest && request.getType().equals(OperationType.OUTCOME)) {
            return updateOutcomeOperation(id, (UpdateOutcomeOperationRequest) request);
        } else if (request instanceof UpdateTransferOperationRequest && request.getType().equals(OperationType.TRANSFER)) {
            return updateTransferOperation(id, (UpdateTransferOperationRequest) request);
        } else {
            throw new OperationException("Incorrect input date");
        }
    }

    private OperationResult updateIncomeOperation(Long id, UpdateIncomeOperationRequest request) {
        Account newAccountTo = request.getAccountToId() != null ? accountService.getById(request.getAccountToId()) : null;
        request.setAccountTo(newAccountTo);
        Operation existedOperation = findStdOpByIdWithExceptionHandler(id);
        try {
            if (request.getAmount() != null) {
                accountService.minus(existedOperation.getAccount().getId(), request.getAmount().doubleValue());
                if (newAccountTo != null) {
                    accountService.plus(newAccountTo.getId(), request.getAmount().doubleValue());
                } else {
                    accountService.plus(existedOperation.getAccount().getId(), request.getAmount().doubleValue());
                }
            }
            BeanUtils.copyNonNullProperties(operationMapper.incomeRequestToOperation(request), existedOperation);
            existedOperation.setUpdateDate(LocalDateTime.now());
            Long operationId = operationRepository.save(existedOperation).getId();
            return OperationResult.builder().result(true).message(String.format("Transfer operation created, №%d", operationId)).build();
        } catch (OperationException exception) {
            //ToDo: сделать логирование
            return OperationResult.builder().result(false).message("Operation declined").build();
        }
    }

    private OperationResult updateOutcomeOperation(Long id, UpdateOutcomeOperationRequest request) {
        Account newAccountFrom = request.getAccountFromId() != null ? accountService.getById(request.getAccountFromId()) : null;
        request.setAccountFrom(newAccountFrom);
        Operation existedOperation = findStdOpByIdWithExceptionHandler(id);
        try {
            if (request.getAmount() != null) {
                if (newAccountFrom != null) {
                    accountService.minus(newAccountFrom.getId(), request.getAmount().doubleValue());
                    try {
                        accountService.minus(existedOperation.getAccount().getId(), request.getAmount().doubleValue());
                    } catch (OperationException exception) {
                        accountService.plus(newAccountFrom.getId(), request.getAmount().doubleValue());
                        return OperationResult.builder().result(false).message("Operation declined").build();
                    }
                } else {
                    accountService.minus(existedOperation.getAccount().getId(), request.getAmount().doubleValue());
                }
                accountService.plus(existedOperation.getAccount().getId(), request.getAmount().doubleValue());
            }
            BeanUtils.copyNonNullProperties(operationMapper.outcomeRequestToOperation(request), existedOperation);
            existedOperation.setUpdateDate(LocalDateTime.now());
            Long operationId = operationRepository.save(existedOperation).getId();
            return OperationResult.builder().result(true).message(String.format("Transfer operation created, №%d", operationId)).build();
        } catch (OperationException exception) {
            //ToDo: сделать логирование
            return OperationResult.builder().result(false).message("Operation declined").build();
        }
    }

    private OperationResult updateTransferOperation(Long id, UpdateTransferOperationRequest request) {
        Account newAccountTo = request.getAccountToId() != null ? accountService.getById(request.getAccountToId()) : null;
        Account newAccountFrom = request.getAccountFromId() != null ? accountService.getById(request.getAccountFromId()) : null;
        request.setAccountTo(newAccountTo);
        request.setAccountFrom(newAccountFrom);
        TransferOperation existedOperation = findTransferOpByIdWithExceptionHandler(id);
        try {
            if (newAccountTo != null) {
                accountService.minus(existedOperation.getAccountTo().getId(), );
            }
            return OperationResult.builder().result(true).message(String.format("Transfer operation created, №%d", operationId)).build();
        } catch (OperationException exception) {
            //ToDo: сделать логирование
            return OperationResult.builder().result(false).message("Operation declined").build();
        }

    }

    @Override
    public OperationResult deleteByIdAndOperationType(Long id, OperationType type) {
        if (type.equals(OperationType.TRANSFER)) {
            return null;
        } else if (type.equals(OperationType.INCOME) || type.equals(OperationType.OUTCOME)){
            return null;
        } else {
            throw new OperationException("Incorrect input date");
        }
    }

    private Operation findStdOpByIdWithExceptionHandler(Long id) {
        return operationRepository.findById(id).orElseThrow(
                () -> new NotFoundException(String.format("Operation with ID %d not found", id)));
    }

    private TransferOperation findTransferOpByIdWithExceptionHandler(Long id) {
        return transferRepository.findById(id).orElseThrow(
                () -> new NotFoundException(String.format("Operation with ID %d not found", id)));
    }
}
