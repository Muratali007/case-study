package kz.diploma.kitaphub.data.repository;

import java.util.Optional;
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
public interface UserRepository extends JpaRepository<User, Long> {
  Optional<User> findUserByUsername(String username);

  @EntityGraph(attributePaths = {"socialLink"})
  @Query("""
      SELECT u from User u where (:search is null or u.username ilike %:search%)
       or (:search is null or u.email ilike %:search%)
      """
  )
  Page<User> adminFindAll(Pageable pageable, String search);
}
