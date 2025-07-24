package dev.phquartin.movieflix.controller.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

@Builder
public record StreamingRequest(
        @NotBlank(message = "Streaming NAME cannot be blank")
        @NotNull(message = "Streaming NAME cannot be null")
        String name
) {}
