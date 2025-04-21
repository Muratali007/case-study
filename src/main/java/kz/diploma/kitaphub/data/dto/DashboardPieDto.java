package kz.diploma.kitaphub.data.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class DashboardPieDto {
  Long accepted;
  Long pending;
  Long rejected;
}
