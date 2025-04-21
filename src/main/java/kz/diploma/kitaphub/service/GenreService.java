package kz.diploma.kitaphub.service;

import java.util.List;
import kz.diploma.kitaphub.data.entity.Genre;
import kz.diploma.kitaphub.data.entity.User;
import kz.diploma.kitaphub.data.repository.GenreRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@Slf4j
public class GenreService {
  private final GenreRepository genreRepository;

  public GenreService(GenreRepository genreRepository) {
    this.genreRepository = genreRepository;
  }

  public List<Genre> getGenres() {
    return genreRepository.findAll();
  }
}
