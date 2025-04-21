package kz.diploma.kitaphub.service;

import java.util.List;
import java.util.Optional;
import kz.diploma.kitaphub.data.dto.AuthorDto;
import kz.diploma.kitaphub.data.entity.Author;
import kz.diploma.kitaphub.data.repository.AuthorRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@Slf4j
public class AuthorService {
  private final AuthorRepository authorRepository;

  public AuthorService(AuthorRepository authorRepository) {
    this.authorRepository = authorRepository;
  }

  public List<String> getAllAuthors(String like) {
    return authorRepository.getAllAuthorsFilter(like);
  }

  public Optional<Author> getAuthorById(Long id) {
    return  authorRepository.findById(id);
  }

  public List<AuthorDto> getAuthorsCarousel() {
    return authorRepository.getAuthorsCarousel();
  }

  public Page<Author> getAuthors(String like, Integer page, String sort, String sortDirection) {
    Pageable pageable =
        PageRequest.of(page, 20, Sort.by(Sort.Direction.fromString(sortDirection), sort));
    return authorRepository.getAllAuthors(pageable, like);
  }
}
