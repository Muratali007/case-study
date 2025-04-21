package kz.diploma.kitaphub.data.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class AuthorDto {
  private Long id;
  private String fullName;
  private String imageUrl;
  private Integer totalBooks;
}
