package dev.phquartin.movieflix.controller;


import dev.phquartin.movieflix.controller.request.CategoryRequest;
import dev.phquartin.movieflix.controller.response.CategoryResponse;
import dev.phquartin.movieflix.service.CategoryService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/categories")
public class CategoryController {

    private final CategoryService service;
    public CategoryController(CategoryService categoryService) {
        this.service = categoryService;
    }

    @PostMapping()
    public ResponseEntity<CategoryResponse> create(@RequestBody @Valid CategoryRequest userCategory) {
        CategoryResponse saved = service.create(userCategory);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(saved.id())
                .toUri();
        return ResponseEntity.created(location).body(saved);
    }

    @GetMapping()
    public ResponseEntity<List<CategoryResponse>> listAll() {
        List<CategoryResponse> allCategories = service.getAll();
        return ResponseEntity.ok().body(allCategories);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CategoryResponse> getById(@PathVariable Long id) {
        CategoryResponse category = service.getById(id);
        return ResponseEntity.ok().body(category);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

}
