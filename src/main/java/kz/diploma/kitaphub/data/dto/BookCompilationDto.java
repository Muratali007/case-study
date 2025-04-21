package kz.diploma.kitaphub.data.dto;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BookCompilationDto {
  private Long id;
  private String name;
  private String description;
  private String imageUrl;
  private List<String> bookIsbns;
  private List<BookCatalogDto> books;
}
