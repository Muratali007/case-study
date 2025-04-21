package kz.diploma.kitaphub.data.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class SaleBookDto {
  private Long id;
  private String isbn;
  private String username;

  private String userImageUrl;
  private String name;
  private String author;
  private String imageUrl;
  private String bookImageUrl;
  private Integer price;
  private boolean used;
  private Long userId;
}
