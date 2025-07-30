package dev.phquartin.movieflix.mapper;

import dev.phquartin.movieflix.controller.request.StreamingRequest;
import dev.phquartin.movieflix.controller.response.StreamingResponse;
import dev.phquartin.movieflix.model.Streaming;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface StreamingMapper {

    Streaming toEntity(StreamingRequest request);

    StreamingResponse toResponse(Streaming streaming);

}
