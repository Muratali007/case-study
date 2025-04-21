package kz.diploma.kitaphub.data.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
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
@Table(name = "authors")
public class Author {
  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "author_seq")
  @SequenceGenerator(name = "author_seq", sequenceName = "authors_id_seq", allocationSize = 1)

  private Long id;

  private String fullName;

  private String shortName;

  @Column(columnDefinition = "TEXT")
  private String description;

  private String imageUrl;

  private Integer totalBooks;

}
