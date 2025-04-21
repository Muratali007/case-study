package kz.diploma.kitaphub.data.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
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

@Table(name = "book_links")
public class BookLink {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String marwinLink;
  private Integer marwinActualPrice;
  private Integer marwinCurrentPrice;

  private String flipLink;
  private Integer flipActualPrice;
  private Integer flipCurrentPrice;

  private String book24_Link;
  private Integer book24_ActualPrice;
  private Integer book24_CurrentPrice;
}
