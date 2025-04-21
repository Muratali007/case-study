package kz.diploma.kitaphub.service;

import java.util.ArrayList;
import java.util.List;
import kz.diploma.kitaphub.data.dto.BookCompilationDto;
import kz.diploma.kitaphub.data.dto.BookRequestInfoDto;
import kz.diploma.kitaphub.data.dto.SaleBookDto;
import kz.diploma.kitaphub.data.entity.Author;
import kz.diploma.kitaphub.data.entity.Book;
import kz.diploma.kitaphub.data.entity.BookCompilation;
import kz.diploma.kitaphub.data.entity.BookCompilationBook;
import kz.diploma.kitaphub.data.entity.User;
import kz.diploma.kitaphub.data.mapper.BookCompilationMapper;
import kz.diploma.kitaphub.data.mapper.BookRequestMapper;
import kz.diploma.kitaphub.data.mapper.SaleBookMapper;
import kz.diploma.kitaphub.data.repository.AuthorRepository;
import kz.diploma.kitaphub.data.repository.BookCompilationBookRepository;
import kz.diploma.kitaphub.data.repository.BookCompilationRepository;
import kz.diploma.kitaphub.data.repository.BookLinkRepository;
import kz.diploma.kitaphub.data.repository.BookRepository;
import kz.diploma.kitaphub.data.repository.BookRequestRepository;
import kz.diploma.kitaphub.data.repository.SaleBookRepository;
import kz.diploma.kitaphub.data.repository.UserBookRepository;
import kz.diploma.kitaphub.data.repository.UserRepository;
import lombok.Getter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class AdminService {
  private final UserRepository userRepository;
  private final UserBookRepository userBookRepository;
  private final BookRequestRepository bookRequestRepository;
  private final SaleBookRepository saleBookRepository;
  private final BookRepository bookRepository;

  private final BookLinkRepository bookLinkRepository;
  private final AuthorRepository authorRepository;
  private final BookRequestMapper bookRequestMapper;

  private final SaleBookMapper saleBookMapper;
  private final BookCompilationRepository bookCompilationRepository;
  private final BookCompilationBookRepository bookCompilationBookRepository;
  private final BookCompilationMapper bookCompilationMapper;

  public AdminService(UserRepository userRepository, UserBookRepository userBookRepository,
                      BookRequestRepository bookRequestRepository,
                      SaleBookRepository saleBookRepository,
                      BookRepository bookRepository,
                      BookLinkRepository bookLinkRepository,
                      AuthorRepository authorRepository,
                      BookRequestMapper bookRequestMapper,
                      SaleBookMapper saleBookMapper,
                      BookCompilationRepository bookCompilationRepository,
                      BookCompilationMapper bookCompilationMapper,
                      BookCompilationBookRepository bookCompilationBookRepository) {
    this.userRepository = userRepository;
    this.userBookRepository = userBookRepository;
    this.bookRequestRepository = bookRequestRepository;
    this.saleBookRepository = saleBookRepository;
    this.bookRepository = bookRepository;
    this.bookLinkRepository = bookLinkRepository;
    this.authorRepository = authorRepository;
    this.bookRequestMapper = bookRequestMapper;
    this.saleBookMapper = saleBookMapper;
    this.bookCompilationRepository = bookCompilationRepository;
    this.bookCompilationMapper = bookCompilationMapper;
    this.bookCompilationBookRepository = bookCompilationBookRepository;
  }

  public Page<User> getAllUsers(Integer page, String search) {
    Pageable pageable = PageRequest.of(page, 20,
        Sort.by(Sort.Direction.fromString("asc"), "id"));
    return userRepository.adminFindAll(pageable, search);
  }

  public void deleteUserById(Long id) {
    bookRequestRepository.deleteBookById(id);
    saleBookRepository.deleteByUserId(id);
    userBookRepository.deleteByUserId(id);
    userRepository.deleteById(id);
  }

  public User editUserInformation(User user) {
    return userRepository.save(user);
  }

  public Page<Book> getAllBooks(Integer page, String search) {
    Pageable pageable = PageRequest.of(page, 20,
        Sort.by(Sort.Direction.fromString("asc"), "createAt"));
    return bookRepository.adminFindAll(pageable, search);
  }

  public void deleteBook(String isbn) {
    bookRequestRepository.deleteByIsbn(isbn);
    saleBookRepository.deleteByIsbn(isbn);
    userBookRepository.deleteByIsbn(isbn);
    bookRepository.deleteByIsbn(isbn);
  }

  public Book editBook(Book book) {
    var bookLink = bookLinkRepository.save(book.getBookLink());
    var author = authorRepository.findById(book.getAuthor().getId()).orElseThrow();
    book.setAuthor(author);
//    book.setBookLink(bookLink);
    return bookRepository.save(book);
  }

  public Book addBook(Book book) {
    var bookLink = bookLinkRepository.save(book.getBookLink());
    var author = authorRepository.findById(book.getAuthor().getId()).orElseThrow();
    book.setBookLink(bookLink);
    book.setAuthor(author);
    return bookRepository.save(book);
  }

  public Page<Author> getAllAuthors(Integer page, String search) {
    Pageable pageable = PageRequest.of(page, 20,
        Sort.by(Sort.Direction.fromString("asc"), "id"));
    return authorRepository.adminFindAll(pageable, search);
  }

  public void deleteAuthor(Long id) {
    bookRepository.deleteByAuthor(id);
    authorRepository.deleteById(id);
  }

  public Author updateAuthor(Author author) {
    return authorRepository.save(author);
  }

  public Author addAuthor(Author author) {
    return authorRepository.save(author);
  }

  public List<Author> getAllAuthorsForBooks() {
    return authorRepository.findAll();
  }

  public Page<BookRequestInfoDto> getAllRequests(Integer page, String search) {
    Pageable pageable = PageRequest.of(page, 20,
        Sort.by(Sort.Direction.fromString("asc"), "id"));
    return bookRequestRepository.adminFindAll(pageable, search)
        .map(bookRequestMapper::toBookToRequestDto);
  }

  public BookRequestInfoDto getRequestById(Long id) {
    return bookRequestMapper.toBookToRequestDto(bookRequestRepository.findById(id).orElseThrow());
  }

  public void deleteRequestById(Long id) {
    bookRequestRepository.deleteById(id);
  }

  public List<SaleBookDto> getAllSaleBooks() {
    return saleBookRepository.findAll().stream().map(saleBookMapper::toSaleBookDto).toList();
  }

  public Page<BookCompilationDto> getAllCompilations(Integer page, String search) {
    Pageable pageable = PageRequest.of(page, 20,
        Sort.by(Sort.Direction.fromString("asc"), "id"));
    return bookCompilationRepository.findByNameContainingIgnoreCase(pageable, search)
        .map(bookCompilationMapper::toBookCompilationDto);
  }

  @Transactional
  public BookCompilationDto addCompilation(BookCompilationDto bookCompilationDto) {
    BookCompilation bookCompilation = new BookCompilation();
    bookCompilation.setName(bookCompilationDto.getName());
    bookCompilation.setDescription(bookCompilationDto.getDescription());
    bookCompilation.setImageUrl(bookCompilationDto.getImageUrl());
    bookCompilation = bookCompilationRepository.save(bookCompilation);

    saveInBookCompilationBook(bookCompilationDto.getBookIsbns(), bookCompilation);

    return bookCompilationMapper.toBookCompilationDto(bookCompilation);
  }

  @Transactional
  public void saveInBookCompilationBook(List<String> bookIsbns, BookCompilation bookCompilation) {
    if (bookIsbns != null && !bookIsbns.isEmpty()) {
      List<BookCompilationBook> bookCompilationBooks = new ArrayList<>();
      for (String isbn : bookIsbns) {
        var book = bookRepository.findByIsbn(isbn).orElseThrow();
        if (book != null) {
          BookCompilationBook bookCompilationBook = new BookCompilationBook();
          bookCompilationBook.setBookCompilation(bookCompilation);
          bookCompilationBook.setBook(book);
          bookCompilationBooks.add(bookCompilationBook);
        }
      }
      bookCompilation.setBookCompilationBooks(bookCompilationBookRepository.saveAll(
          bookCompilationBooks));
      bookCompilationRepository.save(bookCompilation);
    }
  }

  @Transactional
  public BookCompilationDto updateCompilation(Long id, BookCompilationDto bookCompilationDto) {
    BookCompilation bookCompilation = bookCompilationRepository.findById(id)
        .orElseThrow();
    bookCompilation.setName(bookCompilationDto.getName());
    bookCompilation.setDescription(bookCompilationDto.getDescription());
    bookCompilation.setImageUrl(bookCompilationDto.getImageUrl());


    bookCompilationBookRepository.deleteByBookCompilation(bookCompilation);

    saveInBookCompilationBook(bookCompilationDto.getBookIsbns(), bookCompilation);

    return bookCompilationMapper.toBookCompilationDto(bookCompilation);
  }

  @Transactional
  public void deleteCompilation(Long id) {
    bookCompilationBookRepository.deleteByBookCompilation(
        bookCompilationRepository.findById(id).orElseThrow());
    bookCompilationRepository.deleteById(id);
  }
}
