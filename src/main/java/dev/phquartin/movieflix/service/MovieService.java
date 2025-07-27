package dev.phquartin.movieflix.service;

import dev.phquartin.movieflix.controller.request.MovieRequest;
import dev.phquartin.movieflix.controller.response.CategoryResponse;
import dev.phquartin.movieflix.controller.response.MovieResponse;
import dev.phquartin.movieflix.controller.response.StreamingResponse;
import dev.phquartin.movieflix.exception.ResourceAlreadyExistsException;
import dev.phquartin.movieflix.exception.ResourceNotFoundException;
import dev.phquartin.movieflix.mapper.MovieMapper;
import dev.phquartin.movieflix.model.Category;
import dev.phquartin.movieflix.model.Movie;
import dev.phquartin.movieflix.model.Streaming;
import dev.phquartin.movieflix.repository.MovieRepository;
import lombok.AllArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class MovieService {

    private final MovieRepository repository;
    private final MovieMapper mapper;
    private final CategoryService categoryService;
    private final StreamingService streamingService;

    public MovieResponse create(MovieRequest request){


        String normalizedTitle = StringUtils.capitalize(request.title().strip());

        if (repository.existsByTitle(normalizedTitle)) throw new ResourceAlreadyExistsException("A movie with name " + normalizedTitle + " already exists");

        List<Category> categories = getCategories(request);
        List<Streaming> streaming = getStreaming(request);

        Movie entity = mapper.toEntity(request);

        entity.setTitle(normalizedTitle);
        entity.setCategories(categories);
        entity.setStreaming(streaming);

        Movie saved = repository.save(entity);

        return mapper.toResponse(saved);

    }

    public List<MovieResponse> getAll(){
        return repository.findAll().stream()
                .map(mapper::toResponse)
                .toList();
    }

    public MovieResponse getById(Long id){
        Movie movie = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Movie not found"));
        return mapper.toResponse(movie);
    }

    public void delete(Long id){
        if(!repository.existsById(id)) throw new ResourceNotFoundException("Movie not found");
        repository.deleteById(id);
    }

    // TODO: Testar
    public MovieResponse update(Long id, MovieRequest request){
        repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Movie not found"));
        String normalizedTitle = StringUtils.capitalize(request.title().strip());
        if (repository.existsByTitle(normalizedTitle)) throw new ResourceAlreadyExistsException("A movie with name " + normalizedTitle + " already exists");

        List<Streaming> streaming = getStreaming(request);
        List<Category> categories = getCategories(request);

        Movie movie = mapper.toEntity(request);
        movie.setId(id);
        movie.setTitle(normalizedTitle);

        movie.getCategories().clear();
        movie.getStreaming().clear();

        movie.getCategories().addAll(categories);
        movie.getStreaming().addAll(streaming);

        repository.save(movie);
        return mapper.toResponse(movie);

    }

    private List<Category> getCategories(MovieRequest movieRequest){
        List<Long> categories_id = movieRequest.categories();
        List<CategoryResponse> categories_response = categories_id.stream()
                .map(categoryService::getById)
                .toList();

        List<Category> categories = new ArrayList<>();

        categories_response.forEach(categoryResponse -> {
            Category category = new Category();
            category.setId(categoryResponse.id());
            category.setName(categoryResponse.name());
            categories.add(category);
        });

        return categories;

    }

    private List<Streaming> getStreaming(MovieRequest movieRequest){
        List<Long> streaming_id = movieRequest.streaming();
        List<StreamingResponse> streaming_response = streaming_id.stream()
                .map(streamingService::getById)
                .toList();

        List<Streaming> streaming = new ArrayList<>();

        streaming_response.forEach(streamingResponse -> {
            Streaming s = new Streaming();
            s.setId(streamingResponse.id());
            s.setName(streamingResponse.name());
            streaming.add(s);
        });

        return streaming;
    }


}
