package kz.diploma.kitaphub.controller;

import java.util.List;
import kz.diploma.kitaphub.data.dto.BookCompilationDto;
import kz.diploma.kitaphub.data.dto.BookRequestInfoDto;
import kz.diploma.kitaphub.data.dto.SaleBookDto;
import kz.diploma.kitaphub.data.entity.Author;
import kz.diploma.kitaphub.data.entity.Book;
import kz.diploma.kitaphub.data.entity.BookLink;
import kz.diploma.kitaphub.data.entity.CoverType;
import kz.diploma.kitaphub.data.entity.SocialLink;
import kz.diploma.kitaphub.service.AdminService;
import kz.diploma.kitaphub.service.AuthorService;
import kz.diploma.kitaphub.service.BookService;
import kz.diploma.kitaphub.service.UserService;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequestMapping("/admin")
public class AdminController {
  private final AdminService adminService;
  private final UserService userService;
  private final BookService bookService;
  private final AuthorService authorService;

  public AdminController(AdminService adminService, UserService userService,
                         BookService bookService, AuthorService authorService) {
    this.adminService = adminService;
    this.userService = userService;
    this.bookService = bookService;
    this.authorService = authorService;
  }

  @GetMapping("/user/all")
  public ResponseEntity<?> getAllUsers(@RequestParam(defaultValue = "0") Integer page,
                                       @RequestParam(required = false) String search) {
    return ResponseEntity.ok(adminService.getAllUsers(page, search));
  }

  @DeleteMapping("/user/{id}")
  public ResponseEntity<?> deleteUserById(@PathVariable Long id) {
    adminService.deleteUserById(id);
    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
  }

  @PutMapping("/user/edit/{id}")
  public ResponseEntity<?> updateUserById(@PathVariable Long id, @RequestBody UserDto userDto) {
    System.out.println(userDto.toString());
    var user = userService.getUserById(id).orElseThrow();
    user.setName(userDto.getName());
    user.setSurname(userDto.getSurname());
    user.setPhone(userDto.getPhone());
    user.setSocialLink(userDto.getSocialLink());
    return ResponseEntity.ok(adminService.editUserInformation(user));
  }

  @GetMapping("/book/all")
  public ResponseEntity<Page<Book>> getAllBooks(@RequestParam(defaultValue = "0") Integer page,
                                                @RequestParam(required = false) String search) {
    return ResponseEntity.ok(adminService.getAllBooks(page, search));
  }

  @GetMapping("/book/authors")
  public ResponseEntity<List<Author>> getAllAuthorsForBooks() {
    return ResponseEntity.ok(adminService.getAllAuthorsForBooks());
  }

  @DeleteMapping("/book/{isbn}")
  public ResponseEntity<?> deleteBook(@PathVariable String isbn) {
    adminService.deleteBook(isbn);
    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
  }

  @PutMapping("/book/edit/{isbn}")
  public ResponseEntity<?> updateBook(@PathVariable String isbn, @RequestBody BookDto bookDto) {
    var book = bookService.getBookByIsbn(isbn).orElseThrow();
    book.setName(bookDto.getName());
    book.setAuthor(bookDto.getAuthor());
    book.setDescription(bookDto.getDescription());
    book.setYear(bookDto.getYear());
    book.setRating(bookDto.getRating());
    book.setAge(bookDto.getAge());
    book.setImageUrl(bookDto.getImageUrl());
    book.setLanguage(bookDto.getLanguage());
    book.setPublisher(bookDto.getPublisher());
    book.setBookLink(bookDto.getBookLink());
    book.setCover(bookDto.getCover());
    book.setBookPage(bookDto.getBookPage());
    return ResponseEntity.ok(adminService.editBook(book));
  }

  @PostMapping("/book/add")
  public ResponseEntity<?> addBook(@RequestBody Book book) {
    return ResponseEntity.ok(adminService.addBook(book));
  }

  @GetMapping("/author/all")
  public ResponseEntity<Page<Author>> getAllAuthors(@RequestParam(defaultValue = "0") Integer page,
                                                    @RequestParam(required = false) String search) {
    return ResponseEntity.ok(adminService.getAllAuthors(page, search));
  }

  @DeleteMapping("/author/{id}")
  public ResponseEntity<?> deleteAuthor(@PathVariable Long id) {
    adminService.deleteAuthor(id);
    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
  }

  @PutMapping("/author/edit/{id}")
  public ResponseEntity<?> editAuthor(@PathVariable Long id, @RequestBody AuthorDto authorDto) {
    var author = authorService.getAuthorById(id).orElseThrow();
    author.setFullName(authorDto.getFullName());
    author.setImageUrl(authorDto.getImageUrl());
    author.setTotalBooks(authorDto.getTotalBooks());
    author.setDescription(authorDto.getDescription());
    author.setShortName(authorDto.getShortName());
    return ResponseEntity.ok(adminService.updateAuthor(author));
  }

  @PostMapping("/author/add")
  public ResponseEntity<?> addAuthor(@RequestBody Author author) {
    return ResponseEntity.ok(adminService.addAuthor(author));
  }

  @GetMapping("/request/{id}")
  public ResponseEntity<BookRequestInfoDto> getBookRequest(@PathVariable Long id) {
    return ResponseEntity.ok(adminService.getRequestById(id));
  }

  @GetMapping("/request/all")
  public ResponseEntity<Page<BookRequestInfoDto>> getAllRequests(
      @RequestParam(defaultValue = "0") Integer page,
      @RequestParam(required = false) String search) {
    return ResponseEntity.ok(adminService.getAllRequests(page, search));
  }

  @DeleteMapping("/request/{id}")
  public ResponseEntity<?> getAllRequests(@PathVariable Long id) {
    adminService.deleteRequestById(id);
    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
  }

  @GetMapping("/sale/all")
  public ResponseEntity<List<SaleBookDto>> getAllSaleBooks() {
    return ResponseEntity.ok(adminService.getAllSaleBooks());
  }

  @GetMapping("/compilation/all")
  public ResponseEntity<Page<BookCompilationDto>> getAllCompilations(
      @RequestParam(defaultValue = "0") Integer page,
      @RequestParam(required = false) String search) {
    return ResponseEntity.ok(adminService.getAllCompilations(page, search));
  }

  @PostMapping("/compilation/add")
  public ResponseEntity<?> addCompilation(@RequestBody BookCompilationDto bookCompilationDto) {
    return ResponseEntity.ok(adminService.addCompilation(bookCompilationDto));
  }

  @PutMapping("/compilation/edit/{id}")
  public ResponseEntity<?> editCompilation(@PathVariable Long id,
                                           @RequestBody BookCompilationDto bookCompilationDto) {
    return ResponseEntity.ok(adminService.updateCompilation(id, bookCompilationDto));
  }

  @DeleteMapping("/compilation/{id}")
  public ResponseEntity<?> deleteCompilation(@PathVariable Long id) {
    adminService.deleteCompilation(id);
    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
  }


  @Getter
  @Setter
  @ToString
  public static class UserDto {
    private String phone;
    private String name;
    private String surname;
    private SocialLink socialLink;
  }

  @Getter
  @Setter
  public static class BookDto {
    private String name;
    private String description;
    private Integer year;
    private Double rating;
    private String age;
    private Author author;
    private String imageUrl;
    private String language;
    private String publisher;
    private BookLink bookLink;
    private CoverType cover;
    private Integer bookPage;
  }

  @Getter
  @Setter
  public static class AuthorDto {
    private String fullName;
    private String imageUrl;
    private Integer totalBooks;
    private String description;
    private String shortName;
  }
}
