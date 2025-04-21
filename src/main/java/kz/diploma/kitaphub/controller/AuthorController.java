package kz.diploma.kitaphub.controller;

import java.util.List;
import kz.diploma.kitaphub.data.dto.AuthorDto;
import kz.diploma.kitaphub.data.entity.Author;
import kz.diploma.kitaphub.service.AuthorService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequestMapping("/authors")
public class AuthorController {
  private final AuthorService authorService;

  public AuthorController(AuthorService authorService) {
    this.authorService = authorService;
  }

  @GetMapping("/{id}")
  public ResponseEntity<?> getAuthorById(@PathVariable Long id) {
    var author = authorService.getAuthorById(id);

    if (author.isEmpty()) {
      return new ResponseEntity<>("Author not found", HttpStatus.NOT_FOUND);
    }
    return ResponseEntity.ok(author);
  }

  @GetMapping("/filter")
  public ResponseEntity<List<String>> getAllAuthorsFilter(
      @RequestParam(name = "like", required = false) String like) {
    return ResponseEntity.ok(authorService.getAllAuthors(like));
  }

  @GetMapping("/carousel")
  public ResponseEntity<List<AuthorDto>> getAuthorsCarousel() {
    return ResponseEntity.ok(authorService.getAuthorsCarousel());
  }

  @GetMapping
  public ResponseEntity<Page<Author>> getAllAuthors(
      @RequestParam(required = false) String like,
      @RequestParam(defaultValue = "0") Integer page,
      @RequestParam(defaultValue = "totalBooks") String sort,
      @RequestParam(defaultValue = "desc") String direction
  ) {
    return ResponseEntity.ok(
        authorService.getAuthors(like, page, sort, direction));
  }
}
