package kz.diploma.kitaphub.data.repository;

import java.util.List;
import java.util.Optional;
import kz.diploma.kitaphub.data.entity.Book;
import kz.diploma.kitaphub.data.entity.User;
import kz.diploma.kitaphub.data.entity.UserBook;
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
public interface UserBookRepository extends JpaRepository<UserBook, Long> {
  @EntityGraph(attributePaths = {"book", "book.author"})
  @Query("""
      select ub from UserBook ub where ub.user.username = :username
      """)
  List<UserBook> findAllByUsername(@Param("username") String username);

  @EntityGraph(attributePaths = {"book", "book.author"})
  @Query("""
      select ub from UserBook ub where ub.user.username = :username and ub.book.isbn = :isbn
      """)
  Optional<UserBook> findAllByUsernameAndIsbn(String username, String isbn);

  Optional<UserBook> findByBookAndUser(Book book, User user);

  @Query("""
      select ub from UserBook ub where ub.book.isbn = :isbn and ub.have = true
      and ub.user.username != :username
      """)
  List<UserBook> findByIsbnAndHaveIsTrue(String username, String isbn);

  @Modifying
  @Transactional
  @Query(""" 
        delete from UserBook ub where ub.user.id =:userId 
      """)
  void deleteByUserId(Long userId);

  @Modifying
  @Transactional
  @Query(""" 
        delete from UserBook ub where ub.book.isbn =:isbn 
      """)
  void deleteByIsbn(String isbn);

  @Query("""
         select count(*) from UserBook b where b.have = true
      """)
  Integer totalUserHaveBooks();
}
