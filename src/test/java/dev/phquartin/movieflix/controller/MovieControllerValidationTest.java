package dev.phquartin.movieflix.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import dev.phquartin.movieflix.controller.request.MovieRequest; // Importe o seu request
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Testcontainers
class MovieControllerValidationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper; // Converte objetos para JSON

    // --- Configuração do Banco de Dados de Teste ---
    @Container
    static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:15-alpine");

    @DynamicPropertySource
    static void configureProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", postgres::getJdbcUrl);
        registry.add("spring.datasource.username", postgres::getUsername);
        registry.add("spring.datasource.password", postgres::getPassword);
    }

    // ===================================================================
    //  TESTES DO ENDPOINT (CAMADA HTTP)
    // ===================================================================

    @Test
    @DisplayName("POST /movies -> Deve retornar 404 Category not found")
    void whenPostValidRequest_thenShouldReturnCreated() throws Exception {
        MovieRequest validRequest = MovieRequest.builder()
                .title("Inception")
                .rating(new BigDecimal("4.8"))
                .releaseDate(LocalDate.of(2010, 7, 16))
                .categories(List.of(1L))
                .build();

        mockMvc.perform(post("/movies")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(validRequest)))
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("POST /movies -> Deve retornar 400 Bad Request para título em branco")
    void whenPostRequestWithBlankTitle_thenShouldReturnBadRequest() throws Exception {
        MovieRequest invalidRequest = MovieRequest.builder()
                .title("") // Título inválido
                .rating(new BigDecimal("4.8"))
                .releaseDate(LocalDate.of(2010, 7, 16))
                .categories(List.of(1L))
                .build();

        mockMvc.perform(post("/movies")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalidRequest)))
                .andExpect(status().isBadRequest());
    }

    // ===================================================================
    //  TESTES DE VALIDAÇÃO DO DTO (CAMADA DE DADOS)
    // ===================================================================

    @Nested
    @DisplayName("Testes de Validação Direta do MovieRequest DTO")
    class MovieRequestValidationTests {

        @Autowired
        private Validator validator; // Injetado para os testes aninhados

        private MovieRequest.MovieRequestBuilder validRequestBuilder;

        @BeforeEach
        void setUp() {
            this.validRequestBuilder = MovieRequest.builder()
                    .title("Valid Title")
                    .rating(new BigDecimal("4.5"))
                    .releaseDate(LocalDate.now())
                    .categories(List.of(1L));
        }

        @Test
        @DisplayName("Deve ser válido quando todos os campos estão corretos")
        void whenRequestIsValid_thenShouldHaveNoViolations() {
            Set<ConstraintViolation<MovieRequest>> violations = validator.validate(validRequestBuilder.build());
            assertThat(violations).isEmpty();
        }

        @Test
        @DisplayName("Deve falhar quando o título é nulo")
        void whenTitleIsNull_thenShouldHaveViolation() {
            MovieRequest request = validRequestBuilder.title(null).build();
            Set<ConstraintViolation<MovieRequest>> violations = validator.validate(request);
            assertThat(violations).hasSize(2);
        }

        @Test
        @DisplayName("Deve falhar quando o rating é maior que 5.0")
        void whenRatingIsAboveMax_thenShouldHaveViolation() {
            MovieRequest request = validRequestBuilder.rating(new BigDecimal("5.1")).build();
            Set<ConstraintViolation<MovieRequest>> violations = validator.validate(request);
            assertThat(violations).hasSize(1);
            assertThat(violations.iterator().next().getMessage()).isEqualTo("Rating must be max 5.0");
        }

        @Test
        @DisplayName("Deve falhar quando categories é nulo")
        void whenCategoriesIsNull_thenShouldHaveViolation() {
            MovieRequest request = validRequestBuilder.categories(null).build();
            Set<ConstraintViolation<MovieRequest>> violations = validator.validate(request);
            assertThat(violations).hasSize(1);
            assertThat(violations.iterator().next().getMessage()).isEqualTo("Movie CATEGORIES cannot be null");
        }

    }
}