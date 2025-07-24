package dev.phquartin.movieflix.controller.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CategoryRequest(
        @NotBlank(message = "Category NAME cannot be blank")
        @NotNull(message = "Category NAME cannot be null")
        String name
) {}
