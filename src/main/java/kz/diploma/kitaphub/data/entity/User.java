package kz.diploma.kitaphub.data.entity;


import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "users")
public class User {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String username;
  private String name;
  private String surname;
  private String email;
  private String phone;
  private String imageUrl;
  private LocalDate birthDate;
  private String location;

  @OneToOne(cascade = CascadeType.ALL)
  @JoinColumn(name = "social_link_id", referencedColumnName = "id")
  private SocialLink socialLink;
}
