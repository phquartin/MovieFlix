package dev.phquartin.movieflix.controller.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Builder
public record MovieResponse(
        Long id,
        String title,
        String description,
        BigDecimal rating,

        @JsonFormat(shape = JsonFormat.Shape.STRING ,pattern = "dd/MM/yyyy")
        LocalDate releaseDate,

        List<CategoryResponse> categories,
        List<StreamingResponse> streaming
)
{}
