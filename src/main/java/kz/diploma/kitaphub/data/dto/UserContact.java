package kz.diploma.kitaphub.data.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserContact {
  private String phone;
  private String email;
  private String imageUrl;
  private String telegramLink;
  private String instagramLink;
}
