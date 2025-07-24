package dev.phquartin.movieflix.mapper;

import dev.phquartin.movieflix.controller.request.CategoryRequest;
import dev.phquartin.movieflix.controller.response.CategoryResponse;
import dev.phquartin.movieflix.model.Category;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CategoryMapper {

    Category toEntity(CategoryRequest request);

    CategoryResponse toResponse(Category category);
}
