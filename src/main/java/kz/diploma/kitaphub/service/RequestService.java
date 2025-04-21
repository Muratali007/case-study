package kz.diploma.kitaphub.service;

import java.time.LocalDateTime;
import java.util.List;
import kz.diploma.kitaphub.controller.RequestController;
import kz.diploma.kitaphub.data.dto.BookRequestInfoDto;
import kz.diploma.kitaphub.data.entity.Book;
import kz.diploma.kitaphub.data.entity.BookRequest;
import kz.diploma.kitaphub.data.entity.BookRequestStatus;
import kz.diploma.kitaphub.data.entity.BookRequestType;
import kz.diploma.kitaphub.data.entity.User;
import kz.diploma.kitaphub.data.entity.UserBook;
import kz.diploma.kitaphub.data.entity.UserBookStatus;
import kz.diploma.kitaphub.data.mapper.BookRequestMapper;
import kz.diploma.kitaphub.data.repository.BookRepository;
import kz.diploma.kitaphub.data.repository.BookRequestRepository;
import kz.diploma.kitaphub.data.repository.SaleBookRepository;
import kz.diploma.kitaphub.data.repository.UserBookRepository;
import kz.diploma.kitaphub.data.repository.UserRepository;
import kz.diploma.kitaphub.utils.JwtUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@Transactional
@Slf4j
public class RequestService {
  private final BookRequestRepository bookRequestRepository;
  private final UserRepository userRepository;
  private final BookRepository bookRepository;
  private final BookRequestMapper bookRequestMapper;
  private final UserBookRepository userBookRepository;
  private final SaleBookRepository saleBookRepository;

  public RequestService(BookRequestRepository bookRequestRepository, UserRepository userRepository,
                        BookRepository bookRepository, BookRequestMapper bookRequestMapper,
                        UserBookRepository userBookRepository,
                        SaleBookRepository saleBookRepository) {
    this.bookRequestRepository = bookRequestRepository;
    this.userRepository = userRepository;
    this.bookRepository = bookRepository;
    this.bookRequestMapper = bookRequestMapper;
    this.userBookRepository = userBookRepository;
    this.saleBookRepository = saleBookRepository;
  }

  public BookRequest bookRequest(RequestController.BookRequestDto bookRequestDto) {
    BookRequest bookRequest = new BookRequest();
    String username = JwtUtils.getUsername();
    var senderUserId = userRepository.findUserByUsername(username);
    var receiverUserId = userRepository.findById(bookRequestDto.getReceiverUserId());
    var wantBook = bookRepository.findByIsbn(bookRequestDto.getIsbn());
    var exchangeBook = bookRepository.findByIsbn(bookRequestDto.getExchangeIsbn());
    senderUserId.ifPresent(bookRequest::setSenderUser);
    receiverUserId.ifPresent(bookRequest::setReceiverUser);
    wantBook.ifPresent(bookRequest::setBook);
    exchangeBook.ifPresent(bookRequest::setExchangeBook);
    bookRequest.setType(bookRequestDto.getType());
    bookRequest.setPrice(bookRequestDto.getPrice());
    bookRequest.setMessage(bookRequestDto.getMessage());
    bookRequestRepository.save(bookRequest);
    return bookRequest;
  }

  public List<BookRequestInfoDto> getBookRequest() {
    var user = userRepository.findUserByUsername(JwtUtils.getUsername()).orElseThrow();
    return bookRequestRepository.getBookRequest(user.getId()).stream()
        .map(bookRequestMapper::toBookToRequestDto).toList();
  }

  public BookRequestInfoDto getBookRequestById(Long id) {
    return bookRequestMapper.toBookToRequestDto(bookRequestRepository.getBookRequestById(id));
  }

  public ResponseEntity<BookRequestInfoDto> requestBook(Long id,
                                                        BookRequestStatus bookRequestStatus) {
    var bookRequest = bookRequestRepository.getBookRequestById(id);
    if (checkReceiverUser(JwtUtils.getUsername(), bookRequest)) {
      bookRequest.setStatus(bookRequestStatus);
      bookRequest.setEndDate(LocalDateTime.now());
      bookRequestRepository.save(bookRequest);
      if (bookRequestStatus == BookRequestStatus.ACCEPTED) {
        rejectAllRequestsForAcceptedBooks(bookRequest);
        if (bookRequest.getType() == BookRequestType.EXCHANGE) {
          exchangeBooksBetweenUsers(bookRequest);
        } else if (bookRequest.getType() == BookRequestType.BUY) {
          removeBookFromSale(bookRequest.getBook(), bookRequest.getReceiverUser());
          deleteFromUserBook(bookRequest.getBook(), bookRequest.getReceiverUser());
          saveBookForSender(bookRequest.getBook(), bookRequest.getSenderUser());
        }
      }
      return ResponseEntity.ok(bookRequestMapper.toBookToRequestDto(bookRequest));
    } else {
      return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }
  }

  private void saveBookForSender(Book book, User user) {
    UserBook userBook = new UserBook();
    userBook.setHave(Boolean.TRUE);
    userBook.setUser(user);
    userBook.setBook(book);
    userBook.setStatus(UserBookStatus.READ);
    userBookRepository.save(userBook);
  }

  private void deleteFromUserBook(Book book, User user) {
    var userBook = userBookRepository.findByBookAndUser(book, user);
    userBook.ifPresent(userBookRepository::delete);
  }

  private Boolean checkReceiverUser(String username, BookRequest bookRequest) {
    if (bookRequest.getReceiverUser().getUsername().equals(username)) {
      return Boolean.TRUE;
    } else {
      return Boolean.FALSE;
    }
  }

  private void removeBookFromSale(Book book, User user) {
    var userBook = saleBookRepository.findByBookAndUser(book, user);
    userBook.ifPresent(saleBookRepository::delete);
  }

  private void exchangeBooksBetweenUsers(BookRequest bookRequest) {
    Book book1 = bookRequest.getBook();
    Book book2 = bookRequest.getExchangeBook();
    User user1 = bookRequest.getReceiverUser();
    User user2 = bookRequest.getSenderUser();

    updateUserBooks(book1, book2, user1);
    updateUserBooks(book2, book1, user2);
  }

  private void updateUserBooks(Book oldBook, Book newBook, User user) {
    var userBook = userBookRepository.findByBookAndUser(oldBook, user);
    userBook.ifPresent(userBook1 -> {
      userBook1.setHave(Boolean.FALSE);
      userBookRepository.save(userBook1);
    });

    UserBook newUserBook = new UserBook();
    newUserBook.setHave(Boolean.TRUE);
    newUserBook.setBook(newBook);
    newUserBook.setUser(user);
    newUserBook.setStatus(UserBookStatus.READING);
    userBookRepository.save(newUserBook);
  }

  private void rejectAllRequestsForAcceptedBooks(BookRequest bookRequest) {
    String bookIsbn1 = bookRequest.getBook().getIsbn();
    Long userId1 = bookRequest.getReceiverUser().getId();

    bookRequestRepository.rejectedAllRequest(bookIsbn1, userId1);

    if (bookRequest.getType() == BookRequestType.EXCHANGE) {
      String bookIsbn2 = bookRequest.getExchangeBook().getIsbn();
      Long userId2 = bookRequest.getSenderUser().getId();

      bookRequestRepository.rejectedAllRequest(bookIsbn2, userId2);
    }
  }
}
