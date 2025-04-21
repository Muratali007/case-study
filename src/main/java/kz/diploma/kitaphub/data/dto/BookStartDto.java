package kz.diploma.kitaphub.data.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class BookStartDto {
  private String isbn;
  private String name;
  private String author;
  private String imageUrl;
}
