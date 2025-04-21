package kz.diploma.kitaphub.data.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.NamedAttributeNode;
import jakarta.persistence.NamedEntityGraph;
import jakarta.persistence.NamedSubgraph;
import jakarta.persistence.OneToMany;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import java.util.List;
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
@JsonIgnoreProperties(value = {"hibernateLazyInitializer"})
@NamedEntityGraph(name = "BookCompilation.detail",
    attributeNodes = {
        @NamedAttributeNode(value = "bookCompilationBooks", subgraph = "bookCompilationBooksSubgraph")
    },
    subgraphs = {
        @NamedSubgraph(name = "bookCompilationBooksSubgraph",
            attributeNodes = {
                @NamedAttributeNode(value = "book", subgraph = "bookSubgraph")
            }
        ),
        @NamedSubgraph(name = "bookSubgraph",
            attributeNodes = {
                @NamedAttributeNode("author"),
                @NamedAttributeNode("genre"),
                @NamedAttributeNode("bookLink")
            }
        )
    }
)
@Table(name = "book_compilations")
public class BookCompilation {
  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "book_compilations_seq")
  @SequenceGenerator(name = "book_compilations_seq",
          sequenceName = "book_compilations_seq", allocationSize = 1)
  private Long id;
  private String name;
  private String description;
  private String imageUrl;

  @OneToMany(mappedBy = "bookCompilation", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
  private List<BookCompilationBook> bookCompilationBooks;
}
