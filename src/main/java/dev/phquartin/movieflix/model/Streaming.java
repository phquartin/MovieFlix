package dev.phquartin.movieflix.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "streaming")

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Streaming {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(nullable = false, unique = true, length = 100)
    private String name;

}
