package dev.phquartin.movieflix.controller.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import org.hibernate.validator.constraints.Length;

@Builder
public record UserRequest(
        @NotNull(message = "User NAME cannot be null")
        @NotBlank(message = "User NAME cannot be blank")
        @Length(min = 3, max = 100, message = "User NAME must be between 3 and 100 characters")
        String name,
        @NotNull(message = "User EMAIL cannot be null")
        @NotBlank(message = "User EMAIL cannot be blank")
        @Length(min = 5, max = 100, message = "User EMAIL must be between 5 and 100 characters")
        @Email(message = "Invalid EMAIL")
        String email,
        @NotNull(message = "User PASSWORD cannot be null")
        @NotBlank(message = "User PASSWORD cannot be blank")
        @Length(min = 6, max = 100, message = "User PASSWORD must be between 6 and 100 characters")
        String password
) { }
