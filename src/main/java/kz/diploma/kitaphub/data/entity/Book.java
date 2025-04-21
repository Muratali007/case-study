package kz.diploma.kitaphub.data.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;
import org.hibernate.annotations.JdbcType;
import org.hibernate.dialect.PostgreSQLEnumJdbcType;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(value = {"hibernateLazyInitializer"})
@Table(name = "books")
public class Book {
  @Id
  private String isbn;

  @NonNull
  @Column(nullable = false)
  private String name;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "author_id", referencedColumnName = "id")
  private Author author;

  @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "genre_id", referencedColumnName = "id")
  private Genre genre;

  private String description;
  private Integer year;
  private Double rating;
  private String age;
  private String imageUrl;
  private String language;
  private String publisher;

  @Enumerated(EnumType.STRING)
  @JdbcType(PostgreSQLEnumJdbcType.class)
  private CoverType cover;

  private Integer bookPage;

  private LocalDateTime createAt;
  private LocalDateTime updatedAt;

  @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
  @OneToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "book_link_id", referencedColumnName = "id")
  private BookLink bookLink;

  private Long bookClicks;
  private Long redirectClicks;
  private Long goodReadsBookId;

  @PrePersist
  private void prePersist() {
    setCreateAt(LocalDateTime.now());
    setUpdatedAt(LocalDateTime.now());
  }

  @PreUpdate
  private void preUpdate() {
    setUpdatedAt(LocalDateTime.now());
  }
}
