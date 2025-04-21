package kz.diploma.kitaphub.data.repository;

import java.util.List;
import java.util.Optional;
import kz.diploma.kitaphub.data.dto.BookStartDto;
import kz.diploma.kitaphub.data.entity.Book;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional(propagation = Propagation.MANDATORY)
public interface BookRepository extends JpaRepository<Book, Long> {
  @EntityGraph(attributePaths = {"author", "bookLink", "genre"})
  Optional<Book> findByIsbn(String isbn);

  @Modifying
  @Query("UPDATE Book b SET b.bookClicks = b.bookClicks + 1 WHERE b.isbn = :isbn")
  void updateClickByIsbn(@Param("isbn") String isbn);

  @Query("SELECT b.publisher from Book b where "
          + "(:like is null or b.publisher ilike %:like%) group by b.publisher")
  List<String> getAllPublisher(String like);


  @EntityGraph(attributePaths = {"author", "bookLink", "genre"})
  @Query(
      "SELECT b FROM Book b  WHERE (:genres IS NULL OR b.genre.id IN :genres) "
              + "and (:authors IS NULL OR b.author.fullName IN :authors)"
          +
          "and (:publisher IS NULL OR b.publisher IN :publisher) "
              + "and (:languages IS NULL OR b.language IN :languages)")
  Page<Book> findSpecific(Pageable pageable, @Param("genres") List<Long> genres,
                          @Param("authors") List<String> authors,
                          @Param("publisher") List<String> publisher,
                          @Param("languages") List<String> languages);

  @Query("SELECT b FROM Book b order by b.bookClicks desc limit 10")
  List<Book> getPopularCarousel();

  @Query("SELECT b FROM Book b order by b.redirectClicks desc limit 10")
  List<Book> getBestsellersCarousel();

  @Query("SELECT b FROM Book b order by b.createAt desc limit 3")
  List<Book> getNewBooks();

  @Query("SELECT b FROM Book b where b.language = 'Казахский' order by b.bookClicks desc limit 4")
  List<Book> getKazakhBooks();

  @Query("""
      SELECT
      new kz.diploma.kitaphub.data.dto.BookStartDto(b.isbn, b.name, a.fullName, b.imageUrl)
      FROM Book b LEFT JOIN Author a ON b.author.id = a.id
      """)
  List<BookStartDto> getAllBooks();

  @EntityGraph(attributePaths = {"author"})
  List<Book> findByAuthorIdAndNameContainingIgnoreCase(Long authorId, String like, Sort sort);

  @Query(value = """
        SELECT b.* FROM books b WHERE b.name ILIKE %:search% COLLATE "C.UTF-8"
      """, nativeQuery = true)
  List<Book> getBooksFromSearch(@Param("search") String search);


  @Modifying
  @Transactional
  @Query(""" 
        delete from Book b where b.isbn =:isbn 
      """)
  void deleteByIsbn(String isbn);

  @Modifying
  @Transactional
  @Query(""" 
        delete from Book b where b.author.id =:authorId
      """)
  void deleteByAuthor(Long authorId);

  @EntityGraph(attributePaths = {"author", "bookLink", "genre"})
  @Query("""
      SELECT b from Book b where (:search is null or b.name ilike %:search%)
       or (:search is null or b.isbn ilike %:search%) 
      """
  )
  Page<Book> adminFindAll(Pageable pageable, String search);
  
  @Query(value = "select distinct (language) from books", nativeQuery = true)
  List<String> getAllLanguages();
}
