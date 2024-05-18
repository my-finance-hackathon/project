package my.finance.hackathon.app.controller.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import my.finance.hackathon.app.controller.ICategoryController;
import my.finance.hackathon.app.dto.CategoryResponseDto;
import my.finance.hackathon.app.dto.CategoryUpsertRequestDto;
import my.finance.hackathon.app.model.Category;
import my.finance.hackathon.app.service.ICategoryService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/api/v1/category")
public class CategoryController implements ICategoryController {

    private final ICategoryService categoryService;

    @Override
    @GetMapping("/{id}")
    public CategoryResponseDto findById(@PathVariable("id") Long id) {
        log.info("GET: /api/v1/category/{}", id);
        return categoryService.findById(id);
    }

    @Override
    @GetMapping("/name/{name}")
    public CategoryResponseDto findByName(@PathVariable("name") String name) {
        log.info("GET: /api/v1/category/name/{}", name);
        return categoryService.findByName(name);
    }

    @Override
    @GetMapping("/page")
    public Page<CategoryResponseDto> findAll(@PageableDefault(size = 20) Pageable pageable) {
        log.info("GET ALL: /api/v1/category/page, parameters: size - {}, page - {}", pageable.getPageSize(), pageable.getPageNumber());
        return categoryService.findAll(pageable);
    }

    @Override
    @GetMapping("/")
    public List<CategoryResponseDto> findAll() {
        log.info("GET ALL: /api/v1/category/");
        return categoryService.findAll();
    }

    @Override
    @GetMapping("/name/filter")
    public List<String> findAllByPartName(@RequestParam String partName) {
        log.info("GET: /api/v1/category/name/filter, parameters: {}", partName);
        return categoryService.findAllByPartName(partName);
    }

    @Override
    @PostMapping("/")
    @ResponseStatus(HttpStatus.CREATED)
    public void create(@RequestBody CategoryUpsertRequestDto request) {
        log.info("POST: /api/v1/category/, body: {}", request);
        categoryService.create(request);
    }

    @Override
    @PutMapping("/{id}")
    public CategoryResponseDto updateById(@PathVariable("id") Long id, @RequestBody CategoryUpsertRequestDto request) {
        log.info("PUT: /api/v1/category/{}, body: {}", id, request);
        return categoryService.updateById(id, request);
    }

    @Override
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteById(@PathVariable("id") Long id) {
        log.info("DELETE: /api/v1/category/{}", id);
    }
}
