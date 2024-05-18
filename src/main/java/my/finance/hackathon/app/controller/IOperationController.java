package my.finance.hackathon.app.controller;

import my.finance.hackathon.app.dto.operation.*;
import my.finance.hackathon.app.model.OperationType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface IOperationController {

    List<OperationResponseDto> findAll();

    Page<OperationResponseDto> findAll(Pageable pageable);

    OperationResponseDto findByIdAndOperationType(Long id, OperationType type);

    List<OperationResponseDto> findAllByOperationType(OperationType type);

    Page<OperationResponseDto> findAllByOperationType(Pageable pageable, OperationType type);

    List<OperationResponseDto> findStandardOpByCategory(Long categoryId);

    Page<OperationResponseDto> findStandardOpByCategory(Pageable pageable, Long categoryId);

    List<OperationResponseDto> findAllWithFilter(FilterDto filter);

    Page<OperationResponseDto> findStandardOpWithFilter(Pageable pageable, FilterDto filter);

    OperationResult makeTransferOp(CreateOperationIncomeRequest request);

    OperationResult makeTransferOp(CreateOperationOutcomeRequest request);

    OperationResult makeTransferOp(CreateOperationTransferRequest request);

    OperationResult updateById(Long id, UpdateIncomeOperationRequest request);

    OperationResult updateById(Long id, UpdateOutcomeOperationRequest request);

    OperationResult updateById(Long id, UpdateTransferOperationRequest request);

    OperationResult deleteByIdAndOperationType(Long id, OperationType type);
}
