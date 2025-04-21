package kz.diploma.kitaphub.data.repository;

import kz.diploma.kitaphub.data.entity.Genre;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GenreRepository extends JpaRepository<Genre, Long> {
}
