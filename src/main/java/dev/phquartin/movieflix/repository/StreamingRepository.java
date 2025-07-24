package dev.phquartin.movieflix.repository;

import dev.phquartin.movieflix.model.Streaming;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StreamingRepository extends JpaRepository<Streaming, Long> {
    boolean existsByName(String name);
}
