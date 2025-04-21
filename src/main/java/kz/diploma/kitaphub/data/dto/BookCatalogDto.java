package kz.diploma.kitaphub.data.dto;

import lombok.Data;

@Data
public class BookCatalogDto {
  private String isbn;
  private String name;
  private String author;
  private String description;
  private String imageUrl;
  private Long rating;
  private String genre;
  private String publisher;
  private Integer price;
  private Integer oldPrice;
  private Integer year;
  private String language;
}
