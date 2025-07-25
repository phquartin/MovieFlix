package dev.phquartin.movieflix.mapper;

import dev.phquartin.movieflix.controller.request.MovieRequest;
import dev.phquartin.movieflix.controller.response.MovieResponse;
import dev.phquartin.movieflix.model.Category;
import dev.phquartin.movieflix.model.Movie;
import dev.phquartin.movieflix.model.Streaming;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface MovieMapper {

    Movie toEntity(MovieRequest request);

    MovieResponse toResponse(Movie movie);

    List<Category> toCategories(List<Long> ids);
    Category toCategory (Long id);
    List<Streaming> toStreamings(List<Long> ids);

    Streaming toStreaming(Long id);

}
