package my.finance.hackathon.app.service;

import my.finance.hackathon.app.dto.operation.FilterDto;
import my.finance.hackathon.app.dto.operation.OperationResponseDto;
import my.finance.hackathon.app.dto.operation.OperationResult;
import my.finance.hackathon.app.dto.operation.IUpsertOperationRequest;
import my.finance.hackathon.app.model.OperationType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface IOperationService {

    List<OperationResponseDto> findAll();

    Page<OperationResponseDto> findAll(Pageable pageable);

    OperationResponseDto findByIdAndOperationType(Long id, OperationType type);

    List<OperationResponseDto> findAllByOperationType(OperationType type);

    Page<OperationResponseDto> findAllByOperationType(Pageable pageable, OperationType type);

    List<OperationResponseDto> findStandardOpByCategory(Long categoryId);

    Page<OperationResponseDto> findStandardOpByCategory(Pageable pageable, Long categoryId);

    List<OperationResponseDto> findAllWithFilter(FilterDto filter);

    Page<OperationResponseDto> findStandardOpWithFilter(Pageable pageable, FilterDto filter);

    OperationResult makeTransferOp(IUpsertOperationRequest request);

    OperationResult updateById(Long id, IUpsertOperationRequest request);

    OperationResult deleteByIdAndOperationType(Long id, OperationType type);
}
