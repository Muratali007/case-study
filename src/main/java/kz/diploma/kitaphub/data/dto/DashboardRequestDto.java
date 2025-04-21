package kz.diploma.kitaphub.data.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class DashboardRequestDto {
  String date;
  Long buyCount;
  Long exchangeCount;
}
