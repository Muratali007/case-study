package kz.diploma.kitaphub.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import kz.diploma.kitaphub.data.dto.BookCatalogDto;
import kz.diploma.kitaphub.data.dto.BookStartDto;
import kz.diploma.kitaphub.data.entity.Book;
import kz.diploma.kitaphub.data.mapper.BookMapper;
import kz.diploma.kitaphub.data.repository.BookRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@Slf4j
public class BookService {
  private final BookRepository bookRepository;
  private final BookMapper bookMapper;

  public BookService(BookRepository bookRepository, BookMapper bookMapper) {
    this.bookRepository = bookRepository;
    this.bookMapper = bookMapper;
  }

  public List<BookStartDto> getAllBooks() {
    return bookRepository.getAllBooks();
  }

  public Page<BookCatalogDto> getFilterBooks(Integer page, Integer size,
                                             String sort, String sortDirection,
                                             List<Long> genres, List<String> authors,
                                             List<String> publisher, List<String> languages
  ) {
    Pageable pageable = PageRequest.of(page, size,
        Sort.by(Sort.Direction.fromString(sortDirection), sort));
    return bookRepository.findSpecific(pageable, genres, authors, publisher, languages)
        .map(bookMapper::bookToCatalogDto);
  }

  public Optional<Book> getBookByIsbn(String isbn) {
    bookRepository.updateClickByIsbn(isbn);
    return bookRepository.findByIsbn(isbn);
  }

  public List<BookCatalogDto> getPopularCarousel() {
    return bookRepository.getPopularCarousel().stream().map(bookMapper::bookToCatalogDto).collect(
        Collectors.toList());
  }

  public List<BookCatalogDto> getBestsellersCarousel() {
    return bookRepository.getBestsellersCarousel().stream().map(bookMapper::bookToCatalogDto)
        .collect(
            Collectors.toList());
  }

  public List<BookCatalogDto> getNewBooks() {
    return bookRepository.getNewBooks().stream().map(bookMapper::bookToCatalogDto).collect(
        Collectors.toList());
  }

  public List<BookCatalogDto> getKazakhBooks() {
    return bookRepository.getKazakhBooks().stream().map(bookMapper::bookToCatalogDto).collect(
        Collectors.toList());
  }

  public List<String> getAllPublisher(String like) {
    return bookRepository.getAllPublisher(like);
  }

  public List<BookCatalogDto> getBooksByAuthor(Long authorId, String like, String sort,
                                               String direction) {
    return bookRepository.findByAuthorIdAndNameContainingIgnoreCase(authorId, like,
            Sort.by(Sort.Direction.fromString(direction), sort))
        .stream().map(bookMapper::bookToCatalogDto).toList();
  }

  public List<BookCatalogDto> searchByName(String search) {
    return bookRepository.getBooksFromSearch(search)
        .stream().map(bookMapper::bookToCatalogDto)
        .toList();
  }

  public List<String> getAllLanguages() {
    return bookRepository.getAllLanguages();
  }
}
