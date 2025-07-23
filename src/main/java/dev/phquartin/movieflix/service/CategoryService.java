package dev.phquartin.movieflix.service;

import dev.phquartin.movieflix.model.Category;
import dev.phquartin.movieflix.repository.CategoryRepository;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.ObjectNotFoundException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryService {

    private final CategoryRepository categoryRepository;
    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public Category createCategory(Category category) {
        if (category.getName().isEmpty() || category.getName().isBlank()) throw new IllegalArgumentException("Category NAME cannot be null");

        // Deixar um padrão dentro do Banco de Dados
        category.setName(StringUtils.capitalize(category.getName().strip().toLowerCase()));

        try {
            return categoryRepository.save(category);
        } catch (DataIntegrityViolationException e) {
            throw new RuntimeException("A category with name " + category.getName() + " already exists");
        }
    }

    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }
    public Category getCategoryById(Long id) {
        return categoryRepository.findById(id).orElseThrow(() -> new RuntimeException("Category not found"));
    }

    public void deleteCategoryById(Long id) {
        if (!categoryRepository.existsById(id)) throw new RuntimeException("Category not found");
        categoryRepository.deleteById(id);
    }

}
