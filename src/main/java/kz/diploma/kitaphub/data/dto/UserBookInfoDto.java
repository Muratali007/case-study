package kz.diploma.kitaphub.data.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserBookInfoDto {
  private Boolean userBook;
  private Boolean have;
  private Boolean sale;
  private Boolean planning;
}
