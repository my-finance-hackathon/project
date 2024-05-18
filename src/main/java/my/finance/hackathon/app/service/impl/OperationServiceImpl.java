package my.finance.hackathon.app.service.impl;

import lombok.RequiredArgsConstructor;
import my.finance.hackathon.app.dto.operation.*;
import my.finance.hackathon.app.exceptions.OperationException;
import my.finance.hackathon.app.mapper.IOperationMapper;
import my.finance.hackathon.app.model.Category;
import my.finance.hackathon.app.model.Operation;
import my.finance.hackathon.app.model.OperationType;
import my.finance.hackathon.app.model.TransferOperation;
import my.finance.hackathon.app.repository.IOperationRepository;
import my.finance.hackathon.app.repository.ITransferOperationRepository;
import my.finance.hackathon.app.repository.spec.OperationSpec;
import my.finance.hackathon.app.repository.spec.TransferOperationSpec;
import my.finance.hackathon.app.service.ICategoryService;
import my.finance.hackathon.app.service.IOperationService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
public class OperationServiceImpl implements IOperationService {

    private final IOperationMapper operationMapper;

    private final ITransferOperationRepository transferRepository;

    private final IOperationRepository operationRepository;

    private final ICategoryService categoryService;

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
        return null;
    }

    private OperationResult createOutComingOperation(CreateOperationOutcomeRequest request) {
        return null;
    }

    private OperationResult createTransferOperation(CreateOperationTransferRequest request) {
        return null;
    }

    /**
     * Base method to delegate the process to another ones
     */
    @Override
    public OperationResult updateById(Long id, IUpsertOperationRequest request) {
        if (request instanceof UpdateIncomeOperationRequest && request.getType().equals(OperationType.INCOME)) {
            return updateIncomingOperation(id, (UpdateIncomeOperationRequest) request);
        } else if (request instanceof UpdateOutcomeOperationRequest && request.getType().equals(OperationType.OUTCOME)) {
            return updateOutComingOperation(id, (UpdateOutcomeOperationRequest) request);
        } else if (request instanceof UpdateTransferOperationRequest && request.getType().equals(OperationType.TRANSFER)) {
            return updateTransferOperation(id, (UpdateTransferOperationRequest) request);
        } else {
            throw new OperationException("Incorrect input date");
        }
    }

    private OperationResult updateIncomingOperation(Long id, UpdateIncomeOperationRequest request) {
        return null;
    }

    private OperationResult updateOutComingOperation(Long id, UpdateOutcomeOperationRequest request) {
        return null;
    }

    private OperationResult updateTransferOperation(Long id, UpdateTransferOperationRequest request) {
        return null;
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
}
