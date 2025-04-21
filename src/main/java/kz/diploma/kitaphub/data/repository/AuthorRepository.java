package kz.diploma.kitaphub.data.repository;

import java.util.List;
import kz.diploma.kitaphub.data.dto.AuthorDto;
import kz.diploma.kitaphub.data.entity.Author;
import kz.diploma.kitaphub.data.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional(propagation = Propagation.MANDATORY)
public interface AuthorRepository extends JpaRepository<Author, Long> {


  @Query("SELECT a.fullName from Author a where (:like is null or a.fullName ilike %:like%)")
  List<String> getAllAuthorsFilter(String like);

  @Query("SELECT a from Author a where (:like is null or a.fullName ilike %:like%)")
  Page<Author> getAllAuthors(Pageable pageable, String like);

  @Query("""
      SELECT
      new kz.diploma.kitaphub.data.dto.AuthorDto(a.id, a.fullName, a.imageUrl, a.totalBooks)
      FROM Author a
      order by a.totalBooks desc limit 10
      """)
  List<AuthorDto> getAuthorsCarousel();

  @Query("""
      SELECT a from Author a where (:search is null or a.fullName ilike %:search%)
      """
  )
  Page<Author> adminFindAll(Pageable pageable, String search);
}
