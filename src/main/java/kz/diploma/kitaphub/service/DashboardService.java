package kz.diploma.kitaphub.service;

import static kz.diploma.kitaphub.data.entity.BookRequestStatus.ACCEPTED;
import static kz.diploma.kitaphub.data.entity.BookRequestStatus.PENDING;
import static kz.diploma.kitaphub.data.entity.BookRequestStatus.REJECTED;
import static kz.diploma.kitaphub.data.entity.BookRequestType.BUY;
import static kz.diploma.kitaphub.data.entity.BookRequestType.EXCHANGE;

import java.util.List;
import kz.diploma.kitaphub.data.dto.DashboardPieDto;
import kz.diploma.kitaphub.data.dto.DashboardRequestDto;
import kz.diploma.kitaphub.data.repository.AuthorRepository;
import kz.diploma.kitaphub.data.repository.BookRepository;
import kz.diploma.kitaphub.data.repository.BookRequestRepository;
import kz.diploma.kitaphub.data.repository.SaleBookRepository;
import kz.diploma.kitaphub.data.repository.UserBookRepository;
import kz.diploma.kitaphub.data.repository.UserRepository;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class DashboardService {
  private final UserRepository userRepository;
  private final UserBookRepository userBookRepository;
  private final BookRequestRepository bookRequestRepository;
  private final SaleBookRepository saleBookRepository;
  private final BookRepository bookRepository;

  private final AuthorRepository authorRepository;

  public DashboardService(UserRepository userRepository, UserBookRepository userBookRepository,
                          BookRequestRepository bookRequestRepository,
                          SaleBookRepository saleBookRepository,
                          BookRepository bookRepository,
                          AuthorRepository authorRepository) {
    this.userRepository = userRepository;
    this.userBookRepository = userBookRepository;
    this.bookRequestRepository = bookRequestRepository;
    this.saleBookRepository = saleBookRepository;
    this.bookRepository = bookRepository;
    this.authorRepository = authorRepository;
  }

  public DashboardTotalDto getTotals() {
    var res = new DashboardTotalDto();
    res.setTotalBooks((int) bookRepository.count());
    res.setTotalUsers((int) userRepository.count());
    res.setTotalAuthors((int) authorRepository.count());
    res.setTotalRequests((int) bookRequestRepository.count());
    res.setTotalSoldBooks(bookRequestRepository.countByTypeAndStatus(BUY,
        ACCEPTED));
    res.setTotalInSaleBooks((int) saleBookRepository.count());
    res.setTotalExchangedBooks(bookRequestRepository.countByTypeAndStatus(EXCHANGE, ACCEPTED));
    res.setTotalUserHaveBooks(userBookRepository.totalUserHaveBooks());
    return res;
  }

  public List<DashboardRequestDto> getAreaChart() {
    return bookRequestRepository.getDashboard1(BUY, EXCHANGE);
  }

  public List<DashboardRequestDto> getBarChart() {
    return bookRequestRepository.getDashboard2(BUY, EXCHANGE, ACCEPTED);
  }

  public DashboardPieDto getPieChart() {
    return bookRequestRepository.getDashboard3(ACCEPTED, PENDING, REJECTED);
  }

  @Getter
  @Setter
  public static class DashboardTotalDto {
    Integer totalUsers;
    Integer totalBooks;
    Integer totalAuthors;
    Integer totalRequests;
    Integer totalSoldBooks;
    Integer totalExchangedBooks;
    Integer totalInSaleBooks;
    Integer totalUserHaveBooks;
  }
}
