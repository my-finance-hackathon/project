package my.finance.hackathon.app.service.impl;

import lombok.RequiredArgsConstructor;
import my.finance.hackathon.app.dto.CategoryResponseDto;
import my.finance.hackathon.app.dto.CategoryUpsertRequestDto;
import my.finance.hackathon.app.exceptions.AlreadyExistsException;
import my.finance.hackathon.app.exceptions.NotFoundException;
import my.finance.hackathon.app.mapper.ICategoryMapper;
import my.finance.hackathon.app.model.Category;
import my.finance.hackathon.app.repository.ICategoryRepository;
import my.finance.hackathon.app.service.ICategoryService;
import my.finance.hackathon.app.util.BeanUtils;
import my.finance.hackathon.app.util.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements ICategoryService {

    private final ICategoryRepository categoryRepository;

    private final ICategoryMapper categoryMapper;

    @Override
    public CategoryResponseDto findById(Long id) {
        Category category = findByIdWithExceptionHandler(id);
        return categoryMapper.categoryToResponse(category);
    }

    @Override
    public CategoryResponseDto findByName(String name) {
        Category category = findByNameWithExceptionHandler(name);
        return categoryMapper.categoryToResponse(category);
    }

    @Override
    public Page<CategoryResponseDto> findAll(Pageable pageable) {
        Page<Category> categories = categoryRepository.findAll(pageable);
        List<CategoryResponseDto> categoryResponseDtos = categories.getContent()
                .stream()
                .map(categoryMapper::categoryToResponse).toList();
        return new PageImpl<>(categoryResponseDtos, pageable, categories.getTotalElements());
    }

    @Override
    public List<CategoryResponseDto> findAll() {
        List<Category> categories = categoryRepository.findAll();
        return categories.stream().map(categoryMapper::categoryToResponse).toList();
    }

    @Override
    public List<String> findAllByPartName(String partName) {
        return categoryRepository.findByPartName(partName);
    }

    @Override
    public CategoryResponseDto create(CategoryUpsertRequestDto request) {
        validateCategoryByName(request.getName());
        request.setName(StringUtils.toCapitalFirstLetter(request.getName()));
        Category category = categoryRepository.save(categoryMapper.upsertRequestToCategory(request));
        return categoryMapper.categoryToResponse(category);
    }

    @Override
    public CategoryResponseDto updateById(Long id, CategoryUpsertRequestDto request) {
        Category existedCategory = findByIdWithExceptionHandler(id);
        request.setName(StringUtils.toCapitalFirstLetter(request.getName()));
        Category newCategoryProperties = categoryMapper.upsertRequestToCategory(request);
        BeanUtils.copyNonNullProperties(newCategoryProperties, existedCategory);
        Category updatedCategory = categoryRepository.save(existedCategory);
        return categoryMapper.categoryToResponse(updatedCategory);
    }

    @Override
    public void deleteById(Long id) {
        categoryRepository.deleteById(id);
    }

    @Override
    public Category findEntityById(Long id) {
        return findByIdWithExceptionHandler(id);
    }

    private void validateCategoryByName(String name) {
        if (categoryRepository.existsByNameIgnoreCase(name)) {
            throw new AlreadyExistsException(String.format("Category with name \"%s\" already exists", name));
        }
    }

    private Category findByIdWithExceptionHandler(Long id) {
        return categoryRepository.findById(id).orElseThrow(
                () -> new NotFoundException(String.format("Category with id %d not found", id)));
    }

    private Category findByNameWithExceptionHandler(String name) {
        return categoryRepository.findByName(name).orElseThrow(
                () -> new NotFoundException(String.format("Category with id %s not found", name)));
    }
}
