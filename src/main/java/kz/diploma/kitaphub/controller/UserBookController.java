package kz.diploma.kitaphub.controller;

import java.util.List;
import kz.diploma.kitaphub.data.dto.UserBookDto;
import kz.diploma.kitaphub.data.dto.UserBookInfoDto;
import kz.diploma.kitaphub.data.entity.UserBook;
import kz.diploma.kitaphub.data.entity.UserBookStatus;
import kz.diploma.kitaphub.service.BookService;
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
@RequestMapping("/user_books")
public class UserBookController {
  private final UserBookService userBookService;
  private final UserService userService;
  private final BookService bookService;

  public UserBookController(UserBookService userBookService, UserService userService,
                            BookService bookService) {
    this.userBookService = userBookService;
    this.userService = userService;
    this.bookService = bookService;
  }

  @GetMapping
  public List<UserBookDto> getUserBooks() {
//    String username = JwtUtils.getUsername();
    return userBookService.getUserBooks("mura");
  }

  @GetMapping("/user/{isbn}")
  public UserBookDto getUserBookByIsbn(@PathVariable String isbn) {
    return userBookService.getUserBookByUsernameAndIsbn(isbn);
  }

  @GetMapping("/user/{isbn}/info")
  public UserBookInfoDto getUserBookInfoByIsbn(@PathVariable String isbn) {
    return userBookService.getUserBookInfoByUsername(isbn);
  }

  @PostMapping
  public ResponseEntity<Long> addUserBooks(@RequestBody AddUserBookRequest request) {
//    String username = JwtUtils.getUsername();
    var user = userService.getUserByUsername("muramura").orElseThrow();
    var book = bookService.getBookByIsbn(request.getIsbn()).orElseThrow();
    if (!userBookService.checkBookIfExist(user, book)) {
      var userBook = new UserBook();
      userBook.setUser(user);
      userBook.setBook(book);
      userBook.setHave(Boolean.parseBoolean(request.getHave()));
      userBook.setStatus(UserBookStatus.valueOf(request.status));
      userBook.setRating(request.getRating());
      return ResponseEntity.ok(userBookService.addUserBook(userBook));
    } else {
      return new ResponseEntity<>(HttpStatus.CONFLICT);
    }
  }

  @GetMapping("/{id}")
  public ResponseEntity<?> getBookById(@PathVariable Long id) {
    var book = userBookService.getUserBookByIdDto(id);

    if (book.isEmpty()) {
      return new ResponseEntity<>("Book not found", HttpStatus.NOT_FOUND);
    }
    return ResponseEntity.ok(book);
  }

  @PutMapping("/{id}")
  public ResponseEntity<Long> updateBookById(@PathVariable Long id,
                                         @RequestBody AddUserBookRequest request) {
    String username = JwtUtils.getUsername();
    var book = userBookService.getUserBookById(id).orElseThrow();
    if (!book.getUser().getUsername().equals(username)) {
      return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    }
    book.setHave(Boolean.parseBoolean(request.getHave()));
    book.setRating(request.getRating());
    book.setStatus(UserBookStatus.valueOf(request.status));
    return ResponseEntity.ok(userBookService.updateUserBook(book));
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<String> deleteBookByIdg(@PathVariable Long id) {
    String username = JwtUtils.getUsername();
    var book = userBookService.getUserBookById(id).orElseThrow();
    if (!book.getUser().getUsername().equals(username)) {
      return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    }
    return ResponseEntity.ok(userBookService.deleteUserBook(id));
  }

  @Getter
  @Setter
  public static class AddUserBookRequest {
    private String isbn;
    private String have;
    private String status;
    private Integer rating;
  }
}


