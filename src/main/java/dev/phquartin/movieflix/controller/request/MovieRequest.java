package dev.phquartin.movieflix.controller.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Builder
public record MovieRequest(
        @NotNull(message = "Movie TITLE cannot be null")
        @NotBlank(message = "Movie TITLE cannot be blank")
        String title,

        String description,

        @NotNull(message = "Movie RATING cannot be null")
        @DecimalMin(value = "0.0", message = "Rating must be min 0.0")
        @DecimalMax(value = "5.0", message = "Rating must be max 5.0")
        BigDecimal rating,

        @NotNull(message = "Movie RELEASE DATE cannot be null")
        @JsonFormat(shape = JsonFormat.Shape.STRING ,pattern = "dd/MM/yyyy")
        LocalDate releaseDate,

        List<Long> streaming,

        @NotNull(message = "Movie CATEGORIES cannot be null")
        List<Long> categories
) {}
