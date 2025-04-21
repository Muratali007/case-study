package kz.diploma.kitaphub.data.dto;

import kz.diploma.kitaphub.data.entity.UserBookStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class UserBookDto {
  private Long id;
  private String isbn;
  private String name;
  private String author;
  private String imageUrl;
  private String genre;
  private boolean have;
  private UserBookStatus status;
  private Integer rating;
  private String username;
  private String userImageUrl;
  private Long userId;
}
