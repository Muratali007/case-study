package kz.diploma.kitaphub.controller;

import java.util.List;
import kz.diploma.kitaphub.data.dto.SaleBookDto;
import kz.diploma.kitaphub.data.entity.SaleBook;
import kz.diploma.kitaphub.data.entity.UserBookStatus;
import kz.diploma.kitaphub.service.BookService;
import kz.diploma.kitaphub.service.SaleBookService;
import kz.diploma.kitaphub.service.UserBookService;
import kz.diploma.kitaphub.service.UserService;
import kz.diploma.kitaphub.utils.JwtUtils;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequestMapping("/sale_books")
public class SaleBookController {
  private final SaleBookService saleBookService;
  private final UserBookService userBookService;
  private final UserService userService;
  private final BookService bookService;

  public SaleBookController(SaleBookService saleBookService, UserBookService userBookService,
                            UserService userService,
                            BookService bookService) {
    this.saleBookService = saleBookService;
    this.userBookService = userBookService;
    this.userService = userService;
    this.bookService = bookService;
  }

  @GetMapping
  public List<SaleBookDto> getSaleBooks() {
    String username = JwtUtils.getUsername();
    return saleBookService.getSaleBooks(username);
  }

  @PostMapping
  public ResponseEntity<Long> addSaleBooks(@RequestBody AddSaleBookRequest request) {
    String username = JwtUtils.getUsername();
    var user = userService.getUserByUsername(username).orElseThrow();
    var book = bookService.getBookByIsbn(request.getIsbn()).orElseThrow();
    var userBook = userBookService.getUserBookForSale(book, user).orElseThrow();
    if (!userBook.isHave()) {
      return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
    var saleBook = new SaleBook();
    saleBook.setUser(user);
    saleBook.setBook(book);
    saleBook.setUsed(Boolean.parseBoolean(request.getUsed()));
    saleBook.setPrice(request.getPrice());
    saleBook.setImageUrl(request.getImageUrl());
    return ResponseEntity.ok(saleBookService.addSaleBook(saleBook));
  }

  @GetMapping("/{id}")
  public ResponseEntity<?> getBookByIsbn(@PathVariable Long id) {
    var book = saleBookService.getSaleBookByIdDto(id);

    if (book.isEmpty()) {
      return new ResponseEntity<>("Book not found", HttpStatus.NOT_FOUND);
    }
    return ResponseEntity.ok(book);
  }

  @PutMapping("/{id}")
  public ResponseEntity<Long> updateBookByIsbn(@PathVariable Long id,
                                               @RequestBody AddSaleBookRequest request) {
    String username = JwtUtils.getUsername();
    var book = saleBookService.getSaleBookById(id).orElseThrow();
    if (!book.getUser().getUsername().equals(username)) {
      return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    }
    book.setUsed(Boolean.parseBoolean(request.getUsed()));
    book.setPrice(request.getPrice());
    book.setImageUrl(request.getImageUrl());
    return ResponseEntity.ok(saleBookService.updateSaleBook(book));
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<String> deleteBookByIsbn(@PathVariable Long id) {
    String username = JwtUtils.getUsername();
    var book = saleBookService.getSaleBookById(id).orElseThrow();
    if (!book.getUser().getUsername().equals(username)) {
      return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    }
    return ResponseEntity.ok(saleBookService.deleteSaleBook(id));
  }

  @Getter
  @Setter
  public static class AddSaleBookRequest {
    private String isbn;
    private String used;
    private Integer price;
    private String imageUrl;
  }
}
