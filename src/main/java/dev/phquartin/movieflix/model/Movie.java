package dev.phquartin.movieflix.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "movie")

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class Movie {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = 100)
    private String title;

    private String description;

    @Column(nullable = false, precision = 2, scale = 1)
    @DecimalMin(value = "0.0", message = "Rating must be min 0.0")
    @DecimalMax(value = "5.0", message = "Rating must be max 5.0")
    private BigDecimal rating;

    @Column(name = "release_date", nullable = false)
    private LocalDate releaseDate;

    @CreationTimestamp
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @ManyToMany
    @JoinTable(name = "movie_category",
            joinColumns = @JoinColumn(name = "movie_id"),
            inverseJoinColumns = @JoinColumn(name = "category_id")
    )
    private List<Category> categories;

    @ManyToMany
    @JoinTable(name = "movie_streaming",
            joinColumns = @JoinColumn(name = "movie_id"),
            inverseJoinColumns = @JoinColumn(name = "streaming_id")
    )
    private List<Streaming> streaming;


}
