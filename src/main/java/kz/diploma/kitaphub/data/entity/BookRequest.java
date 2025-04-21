package kz.diploma.kitaphub.data.entity;

import jakarta.persistence.Column;
import java.time.LocalDateTime;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.JdbcType;
import org.hibernate.dialect.PostgreSQLEnumJdbcType;


@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "book_requests")
public class BookRequest {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne
  @JoinColumn(name = "sender_user_id", referencedColumnName = "id", nullable = false)
  private User senderUser;

  @ManyToOne
  @JoinColumn(name = "receiver_user_id", referencedColumnName = "id", nullable = false)
  private User receiverUser;

  @ManyToOne
  @JoinColumn(name = "isbn", referencedColumnName = "isbn", nullable = false)
  private Book book;

  @ManyToOne
  @JoinColumn(name = "exchange_isbn", referencedColumnName = "isbn")
  private Book exchangeBook;

  @Enumerated(EnumType.STRING)
  @JdbcType(PostgreSQLEnumJdbcType.class)
  private BookRequestType type;

  @Enumerated(EnumType.STRING)
  @JdbcType(PostgreSQLEnumJdbcType.class)
  private BookRequestStatus status = BookRequestStatus.PENDING;

  private Integer price;

  @Column(columnDefinition = "TEXT")
  private String message;

  private LocalDateTime beginDate = LocalDateTime.now();
  private LocalDateTime endDate;
}
