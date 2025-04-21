package kz.diploma.kitaphub.controller;

import java.util.List;
import kz.diploma.kitaphub.data.entity.Genre;
import kz.diploma.kitaphub.service.GenreService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequestMapping("/genres")
public class GenreController {
  private final GenreService genreService;

  public GenreController(GenreService genreService) {
    this.genreService = genreService;
  }

  @GetMapping
  public ResponseEntity<List<Genre>> getAllGenres() {
    return ResponseEntity.ok(genreService.getGenres());
  }
}
