package kz.diploma.kitaphub.data.repository;

import java.util.List;
import java.util.Optional;
import kz.diploma.kitaphub.data.entity.Book;
import kz.diploma.kitaphub.data.entity.SaleBook;
import kz.diploma.kitaphub.data.entity.User;
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
public interface SaleBookRepository extends JpaRepository<SaleBook, Long> {
  @EntityGraph(attributePaths = {"book", "book.author"})
  @Query("""
        select sb from SaleBook sb where sb.user.username = :username
        """)
  List<SaleBook> findAllByUsername(@Param("username") String username);

  @Query("""
        select sb from SaleBook sb where sb.book.isbn = :isbn and sb.user.username != :username
        """)
  List<SaleBook> findAllByIsbn(@Param("username") String username, @Param("isbn") String isbn);

  Optional<SaleBook> findByBookAndUser(Book book, User user);

  @Modifying
  @Transactional
  @Query(""" 
        delete from SaleBook sb where sb.user.id =:userId 
      """)
  void deleteByUserId(Long userId);

  @Modifying
  @Transactional
  @Query(""" 
        delete from SaleBook sb where sb.book.isbn =:isbn 
      """)
  void deleteByIsbn(String isbn);
}
