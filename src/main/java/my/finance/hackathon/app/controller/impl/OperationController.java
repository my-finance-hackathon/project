package my.finance.hackathon.app.controller.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import my.finance.hackathon.app.controller.IOperationController;
import my.finance.hackathon.app.dto.operation.*;
import my.finance.hackathon.app.model.OperationType;
import my.finance.hackathon.app.service.IOperationService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/operation")
@RequiredArgsConstructor
@Slf4j
public class OperationController implements IOperationController {

    private final IOperationService operationService;

    @Override
    @GetMapping("/")
    public List<OperationResponseDto> findAll() {
        log.info("GET ALL: /api/v1/operation");
        return operationService.findAll();
    }

    @Override
    @GetMapping("/page")
    public Page<OperationResponseDto> findAll(@PageableDefault(size = 20) Pageable pageable) {
        log.info("GET ALL: /api/v1/operation/page, parameters: size - {}, page - {}", pageable.getPageSize(), pageable.getPageNumber());
        return operationService.findAll(pageable);
    }

    @Override
    @GetMapping("/find-exact")
    public OperationResponseDto findByIdAndOperationType(@RequestParam Long id, @RequestParam OperationType type) {
        log.info("GET ALL: /api/v1/operation/find-exact, parameters: id - {}, type - {}", id, type);
        return operationService.findByIdAndOperationType(id, type);
    }

    @Override
    @GetMapping("/type")
    public List<OperationResponseDto> findAllByOperationType(@RequestParam OperationType type) {
        log.info("GET ALL: /api/v1/operation/type, parameters: type - {}", type);
        return operationService.findAllByOperationType(type);
    }

    @Override
    @GetMapping("/type/page")
    public Page<OperationResponseDto> findAllByOperationType(@PageableDefault(size = 20) Pageable pageable, @RequestParam OperationType type) {
        log.info("GET ALL: /api/v1/operation/type/page, parameters: type - {}, size - {}, page - {}", type, pageable.getPageSize(), pageable.getPageNumber());
        return operationService.findAllByOperationType(pageable, type);
    }

    @Override
    @GetMapping("/category")
    public List<OperationResponseDto> findStandardOpByCategory(@RequestParam Long categoryId) {
        log.info("GET ALL: /api/v1/operation/category, parameters: category id - {}", categoryId);
        return operationService.findStandardOpByCategory(categoryId);
    }

    @Override
    @GetMapping("/category/page")
    public Page<OperationResponseDto> findStandardOpByCategory(@PageableDefault(size = 20) Pageable pageable, @RequestParam Long categoryId) {
        log.info("GET ALL: /api/v1/operation/category/page, parameters: category id - {}, size - {}, page - {}", categoryId, pageable.getPageSize(), pageable.getPageNumber());
        return operationService.findStandardOpByCategory(pageable, categoryId);
    }

    @Override
    @PostMapping("/filter")
    public List<OperationResponseDto> findAllWithFilter(@RequestBody FilterDto filter) {
        log.info("POST FIND ALL (with filter): /api/v1/operation/filter, body: filter - {}", filter);
        return operationService.findAllWithFilter(filter);
    }

    @Override
    @PostMapping("/filter/page")
    public Page<OperationResponseDto> findStandardOpWithFilter(@PageableDefault(size = 20) Pageable pageable, @RequestBody FilterDto filter) {
        log.info("POST FIND ALL (with filter): /api/v1/operation/filter, body: filter - {}, size - {}, page - {}", filter, pageable.getPageSize(), pageable.getPageNumber());
        return operationService.findAllWithFilter(pageable, filter);
    }

    @Override
    @PostMapping("/income")
    public OperationResult makeTransferOp(@RequestBody CreateOperationIncomeRequest request) {
        log.info("POST : /api/v1/operation/income, body - {}", request);
        return operationService.makeOperation(request);
    }

    @Override
    @PostMapping("/outcome")
    public OperationResult makeTransferOp(@RequestBody CreateOperationOutcomeRequest request) {
        log.info("POST : /api/v1/operation/outcome, body - {}", request);
        return operationService.makeOperation(request);
    }

    @Override
    @PostMapping("/transfer")
    public OperationResult makeTransferOp(@RequestBody CreateOperationTransferRequest request) {
        log.info("POST : /api/v1/operation/transfer, body - {}", request);
        return operationService.makeOperation(request);
    }

    @Override
    @PutMapping("/income")
    public OperationResult updateById(@PathVariable("id") Long id, @RequestBody UpdateIncomeOperationRequest request) {
        log.info("PUT : /api/v1/operation/income, id - {}, body - {}", id, request);
        return operationService.updateById(id, request);
    }

    @Override
    @PutMapping("/outcome")
    public OperationResult updateById(@PathVariable("id") Long id, @RequestBody UpdateOutcomeOperationRequest request) {
        log.info("PUT : /api/v1/operation/outcome, id - {}, body - {}", id, request);
        return operationService.updateById(id, request);
    }

    @Override
    @PutMapping("/transfer")
    public OperationResult updateById(@PathVariable("id") Long id, @RequestBody UpdateTransferOperationRequest request) {
        log.info("PUT : /api/v1/operation/transfer, id - {}, body - {}", id, request);
        return operationService.updateById(id, request);
    }

    @Override
    @DeleteMapping("/{id}")
    public OperationResult deleteByIdAndOperationType(@PathVariable("id") Long id, @RequestParam OperationType type) {
        log.info("DELETE : /api/v1/operation, id - {}, type - {}", id, type);
        return operationService.deleteByIdAndOperationType(id, type);
    }
}
