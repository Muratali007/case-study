package kz.diploma.kitaphub.service;

import java.util.List;
import java.util.Optional;
import kz.diploma.kitaphub.data.dto.UserBookDto;
import kz.diploma.kitaphub.data.dto.UserBookInfoDto;
import kz.diploma.kitaphub.data.entity.Book;
import kz.diploma.kitaphub.data.entity.User;
import kz.diploma.kitaphub.data.entity.UserBook;
import kz.diploma.kitaphub.data.entity.UserBookStatus;
import kz.diploma.kitaphub.data.mapper.UserBookMapper;
import kz.diploma.kitaphub.data.repository.UserBookRepository;
import kz.diploma.kitaphub.utils.JwtUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@Slf4j
public class UserBookService {
  private final UserBookRepository userBookRepository;
  private final UserBookMapper userBookMapper;

  public UserBookService(UserBookRepository userBookRepository, UserBookMapper userBookMapper) {
    this.userBookRepository = userBookRepository;
    this.userBookMapper = userBookMapper;
  }

  public List<UserBookDto> getUserBooks(String username) {
    return userBookRepository.findAllByUsername(username).stream()
        .map(userBookMapper::toUserBookDto)
        .toList();
  }

  public UserBookDto getUserBookByUsernameAndIsbn(String isbn) {
    String username = JwtUtils.getUsername();
    var book = userBookRepository.findAllByUsernameAndIsbn(username, isbn).orElseThrow();
    return userBookMapper.toUserBookDto(book);
  }

  public UserBookInfoDto getUserBookInfoByUsername(String isbn) {
    String username = JwtUtils.getUsername();
    var book = userBookRepository.findAllByUsernameAndIsbn(username, isbn);
    var info = new UserBookInfoDto();
    info.setUserBook(book.isPresent());
    if (book.isPresent()) {
      info.setHave(book.get().isHave());
      info.setPlanning(book.get().getStatus().equals(UserBookStatus.PLANNING));
    }
    return info;
  }

  public Optional<UserBookDto> getUserBookByIdDto(Long id) {
    return userBookRepository.findById(id).map(userBookMapper::toUserBookDto);
  }

  public Optional<UserBook> getUserBookById(Long id) {
    return userBookRepository.findById(id);
  }

  public Optional<UserBook> getUserBookForSale(Book book, User user) {
    return userBookRepository.findByBookAndUser(book, user);
  }

  public Long addUserBook(UserBook userBook) {
    var created = userBookRepository.save(userBook);
    return created.getId();
  }

  public Long updateUserBook(UserBook userBook) {
    var updated = userBookRepository.save(userBook);
    return updated.getId();
  }

  public String deleteUserBook(Long id) {
    userBookRepository.deleteById(id);
    return "Book deleted successfully";
  }

  public List<UserBookDto> getBooksForExchangeByIbn(String username, String isbn) {
    List<UserBook> userBooks = userBookRepository.findByIsbnAndHaveIsTrue(username, isbn);
    return userBooks.stream().map(userBookMapper::toUserBookDto).toList();
  }

  public Boolean checkBookIfExist(User user, Book book) {
    var userBook = userBookRepository.findByBookAndUser(book, user);
    return userBook.isPresent();
  }
}
