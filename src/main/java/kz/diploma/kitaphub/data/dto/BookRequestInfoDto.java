package kz.diploma.kitaphub.data.dto;

import java.time.LocalDateTime;
import kz.diploma.kitaphub.data.entity.BookRequestStatus;
import kz.diploma.kitaphub.data.entity.BookRequestType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class BookRequestInfoDto {
  private Long id;
  private Long senderUserId;
  private String senderUsername;
  private String senderImageUrl;
  private Long receiverUserId;
  private String receiverUsername;
  private String receiverImageUrl;
  private String isbn;
  private String bookName;
  private String bookImageUrl;
  private String exchangeIsbn;
  private String exchangeName;
  private String exchangeImageUrl;
  private BookRequestType type;
  private BookRequestStatus status;
  private Integer price;
  private String message;
  private LocalDateTime beginDate;
  private LocalDateTime endDate;
}
