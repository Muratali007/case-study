package kz.diploma.kitaphub.data.entity;

import jakarta.persistence.Entity;
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

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "sold_books")
public class SoldBook {
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

}
