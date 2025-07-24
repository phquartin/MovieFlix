package dev.phquartin.movieflix.controller;

import dev.phquartin.movieflix.controller.request.StreamingRequest;
import dev.phquartin.movieflix.controller.response.StreamingResponse;
import dev.phquartin.movieflix.service.StreamingService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/streaming")
public class StreamingController {

    private final StreamingService service;
    public StreamingController(StreamingService service) {
        this.service = service;
    }

    @PostMapping()
    public ResponseEntity<StreamingResponse> create(@RequestBody @Valid StreamingRequest request) {
        StreamingResponse saved = service.create(request);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(saved.id())
                .toUri();
        return ResponseEntity.created(location).body(saved);
    }

    @GetMapping()
    public ResponseEntity<List<StreamingResponse>> listAll() {
        List<StreamingResponse> allCategories = service.getAll();
        return ResponseEntity.ok().body(allCategories);
    }

    @GetMapping("/{id}")
    public ResponseEntity<StreamingResponse> getById(@PathVariable Long id) {
        StreamingResponse category = service.getById(id);
        return ResponseEntity.ok().body(category);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

}
