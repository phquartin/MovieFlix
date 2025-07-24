package dev.phquartin.movieflix.service;

import dev.phquartin.movieflix.controller.request.StreamingRequest;
import dev.phquartin.movieflix.controller.response.StreamingResponse;
import dev.phquartin.movieflix.exception.ResourceAlreadyExistsException;
import dev.phquartin.movieflix.mapper.StreamingMapper;
import dev.phquartin.movieflix.model.Streaming;
import dev.phquartin.movieflix.repository.StreamingRepository;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StreamingService {

    private final StreamingRepository repository;
    private final StreamingMapper mapper;
    public StreamingService(StreamingRepository repository, StreamingMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    public StreamingResponse create(StreamingRequest request){

        String normalizedName = StringUtils.capitalize(request.name().strip().toLowerCase());

        if (repository.existsByName(normalizedName)) throw new ResourceAlreadyExistsException("A streaming with name " + normalizedName + " already exists");

        Streaming entity = mapper.toEntity(request);

        entity.setName(normalizedName);
        Streaming saved = repository.save(entity);

        return mapper.toResponse(saved);
    }

    public List<StreamingResponse> getAll(){
        return repository.findAll().stream().map(mapper::toResponse).toList();
    }

    public StreamingResponse getById(Long id){
        Streaming streaming = repository.findById(id).orElseThrow(() -> new ResourceAlreadyExistsException("Streaming not found"));
        return mapper.toResponse(streaming);
    }

    public void delete(Long id){
        if(!repository.existsById(id)) throw new ResourceAlreadyExistsException("Streaming not found");
        repository.deleteById(id);
    }

}
