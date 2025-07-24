package dev.phquartin.movieflix.service;

import dev.phquartin.movieflix.controller.request.CategoryRequest;
import dev.phquartin.movieflix.controller.response.CategoryResponse;
import dev.phquartin.movieflix.exception.ResourceAlreadyExistsException;
import dev.phquartin.movieflix.exception.ResourceNotFoundException;
import dev.phquartin.movieflix.mapper.CategoryMapper;
import dev.phquartin.movieflix.model.Category;
import dev.phquartin.movieflix.repository.CategoryRepository;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryService {

    private final CategoryRepository repository;
    private final CategoryMapper mapper;
    public CategoryService(CategoryRepository categoryRepository, CategoryMapper mapper) {
        this.repository = categoryRepository;
        this.mapper = mapper;
    }

    public CategoryResponse create(CategoryRequest request) {

        String normalizedName = StringUtils.capitalize(request.name().strip().toLowerCase());

        if (repository.existsByName(normalizedName)) throw new ResourceAlreadyExistsException("A category with name " + normalizedName + " already exists");

        Category entity = mapper.toEntity(request);

        Category savedEntity = repository.save(entity);

        return mapper.toResponse(savedEntity);
    }

    public List<CategoryResponse> getAll() {
        return repository.findAll()
                .stream()
                .map(mapper::toResponse)
                .toList();
    }
    public CategoryResponse getById(Long id) {
        Category entity = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Category not found"));
        return mapper.toResponse(entity);
    }

    public void delete(Long id) {
        if (!repository.existsById(id)) throw new ResourceNotFoundException("Category not found");
        repository.deleteById(id);
    }

}
