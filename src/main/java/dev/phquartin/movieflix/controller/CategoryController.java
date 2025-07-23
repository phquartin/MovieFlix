package dev.phquartin.movieflix.controller;


import dev.phquartin.movieflix.model.Category;
import dev.phquartin.movieflix.service.CategoryService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/categories")
public class CategoryController {

    private final CategoryService categoryService;
    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @PostMapping()
    public ResponseEntity<Void> createCategory(@RequestBody Category userCategory) {
        Category saved = categoryService.createCategory(userCategory);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(saved.getId())
                .toUri();
        return ResponseEntity.created(location).build();
    }

    @GetMapping()
    public ResponseEntity<List<Category>> getAllCategories() {
        List<Category> allCategories = categoryService.getAllCategories();
        return ResponseEntity.ok().body(allCategories);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Category> getCategoryById(@PathVariable Long id) {
        Category category = categoryService.getCategoryById(id);
        return ResponseEntity.ok().body(category);
    }

}
