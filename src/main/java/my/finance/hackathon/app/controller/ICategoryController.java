package my.finance.hackathon.app.controller;

import my.finance.hackathon.app.dto.CategoryResponseDto;
import my.finance.hackathon.app.dto.CategoryUpsertRequestDto;
import my.finance.hackathon.app.model.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ICategoryController {

    CategoryResponseDto findById(Long id);

    CategoryResponseDto findByName(String name);

    Page<CategoryResponseDto> findAll(Pageable pageable);

    List<CategoryResponseDto> findAll();

    List<String> findAllByPartName(String partName);

    void create(CategoryUpsertRequestDto request);

    CategoryResponseDto updateById(Long id, CategoryUpsertRequestDto request);

    void deleteById(Long id);
}
