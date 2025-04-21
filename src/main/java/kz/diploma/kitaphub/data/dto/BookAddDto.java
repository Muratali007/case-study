package kz.diploma.kitaphub.data.dto;

import kz.diploma.kitaphub.data.entity.User;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

@Getter
@Setter
public class BookAddDto {
  @NonNull
  private String isbn;
  private String name;
  private String imageUrl;
  private Integer year;
  private String description;
  private String language;
  private String publisher;
  private User user;
}
