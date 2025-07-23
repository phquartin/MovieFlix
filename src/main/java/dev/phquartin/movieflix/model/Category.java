package dev.phquartin.movieflix.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "category")

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = 100)
    private String name;

}
