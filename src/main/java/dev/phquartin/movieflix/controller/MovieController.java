package dev.phquartin.movieflix.controller;

import dev.phquartin.movieflix.controller.request.MovieRequest;
import dev.phquartin.movieflix.controller.response.MovieResponse;
import dev.phquartin.movieflix.service.MovieService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/movies")
public class MovieController {

    private final MovieService service;
    public MovieController(MovieService service) {
        this.service = service;
    }

    @PostMapping()
    public ResponseEntity<MovieResponse> create(@RequestBody @Valid MovieRequest request) {
        MovieResponse saved = service.create(request);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(saved.id())
                .toUri();
        return ResponseEntity.created(location).body(saved);
    }

    @GetMapping
    public ResponseEntity<List<MovieResponse>> listAll() {
        return ResponseEntity.ok().body(service.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<MovieResponse> getById(@PathVariable Long id) {
        return ResponseEntity.ok().body(service.getById(id));
    }
    @GetMapping("/search/{title}")
    public ResponseEntity<List<MovieResponse>> searchByTitle(@PathVariable String title) {
        List<MovieResponse> movies = service.getAll()
                .stream()
                .filter(movie -> movie.title().contains(title))
                .toList();
        return ResponseEntity.ok().body(movies);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<MovieResponse> update(@PathVariable Long id, @RequestBody @Valid MovieRequest request) {
        MovieResponse saved = service.update(id, request);
        return ResponseEntity.ok().body(saved);
    }


}
