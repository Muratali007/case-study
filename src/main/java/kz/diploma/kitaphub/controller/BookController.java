package kz.diploma.kitaphub.controller;

import java.util.List;
import kz.diploma.kitaphub.data.dto.BookCatalogDto;
import kz.diploma.kitaphub.data.dto.BookStartDto;
import kz.diploma.kitaphub.data.dto.SaleBookDto;
import kz.diploma.kitaphub.data.dto.UserBookDto;
import kz.diploma.kitaphub.service.BookService;
import kz.diploma.kitaphub.service.SaleBookService;
import kz.diploma.kitaphub.service.UserBookService;
import kz.diploma.kitaphub.utils.JwtUtils;
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
@RequestMapping("/books")
public class BookController {
  private final BookService bookService;
  private final UserBookService userBookService;
  private final SaleBookService saleBookService;

  public BookController(BookService bookService, UserBookService userBookService,
                        SaleBookService saleBookService) {
    this.bookService = bookService;
    this.userBookService = userBookService;
    this.saleBookService = saleBookService;
  }

  @GetMapping
  public ResponseEntity<Page<BookCatalogDto>> getAllBooksWithFilters(
      @RequestParam(defaultValue = "0") Integer page,
      @RequestParam(defaultValue = "20") Integer size,
      @RequestParam(defaultValue = "bookClicks") String sort,
      @RequestParam(defaultValue = "desc") String direction,
      @RequestParam(name = "genres", required = false) List<Long> genres,
      @RequestParam(name = "authors", required = false) List<String> authors,
      @RequestParam(name = "publisher", required = false) List<String> publisher,
      @RequestParam(name = "languages", required = false) List<String> languages
  ) {
    return ResponseEntity.ok(
        bookService.getFilterBooks(page, size, sort,
                direction, genres, authors, publisher, languages));
  }

  @GetMapping("/all")
  public ResponseEntity<List<BookStartDto>> getAllBooks() {
    return ResponseEntity.ok(
        bookService.getAllBooks());
  }

  @GetMapping("/{isbn}")
  public ResponseEntity<?> getBookByIsbn(@PathVariable String isbn) {
    var book = bookService.getBookByIsbn(isbn);

    if (book.isEmpty()) {
      return new ResponseEntity<>("Book not found", HttpStatus.NOT_FOUND);
    }
    return ResponseEntity.ok(book);
  }

  @GetMapping("/publisher")
  public ResponseEntity<List<String>> getAllPublisher(
      @RequestParam(name = "like", required = false) String like) {
    return ResponseEntity.ok(bookService.getAllPublisher(like));
  }

  @GetMapping("/bestsellers/carousel")
  public ResponseEntity<List<BookCatalogDto>> getBestsellersCarousel() {
    return ResponseEntity.ok(bookService.getBestsellersCarousel());
  }

  @GetMapping("/popular/carousel")
  public ResponseEntity<List<BookCatalogDto>> getPopularCarousel() {
    return ResponseEntity.ok(bookService.getPopularCarousel());
  }

  @GetMapping("/new")
  public ResponseEntity<List<BookCatalogDto>> getNewBooks() {
    return ResponseEntity.ok(bookService.getNewBooks());
  }

  @GetMapping("/kazakh")
  public ResponseEntity<List<BookCatalogDto>> getKazakhBooks() {
    return ResponseEntity.ok(bookService.getKazakhBooks());
  }

  @GetMapping("/author")
  public ResponseEntity<List<BookCatalogDto>> getBookByAuthor(
      @RequestParam Long authorId,
      @RequestParam(defaultValue = "bookClicks") String sort,
      @RequestParam(defaultValue = "desc") String direction,
      @RequestParam(required = false) String like
  ) {
    return ResponseEntity.ok(bookService.getBooksByAuthor(authorId, like, sort, direction));
  }

  @GetMapping("/search")
  public ResponseEntity<List<BookCatalogDto>> getSearchedBooks(
      @RequestParam(defaultValue = "") String search
  ) {
    return ResponseEntity.ok(bookService.searchByName(search));
  }

  @GetMapping("/{isbn}/books-exchange")
  public ResponseEntity<List<UserBookDto>> getBooksForExchangeByIsbn(
      @PathVariable String isbn
  ) {
    String username = JwtUtils.getUsername();
    return ResponseEntity.ok(userBookService.getBooksForExchangeByIbn(username, isbn));
  }

  @GetMapping("/{isbn}/books-sale")
  public ResponseEntity<List<SaleBookDto>> getBooksForSaleIsbn(
      @PathVariable String isbn
  ) {
    String username = JwtUtils.getUsername();
    return ResponseEntity.ok(saleBookService.getBooksForSaleByIbn(username, isbn));
  }

  @GetMapping("/languages")
  public ResponseEntity<List<String>> getLanguages() {
    return ResponseEntity.ok(bookService.getAllLanguages());
  }
}
