package kz.diploma.kitaphub.data.repository;

import java.util.List;
import java.util.Optional;
import kz.diploma.kitaphub.data.entity.BookCompilation;
import lombok.NonNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional(propagation = Propagation.MANDATORY)
public interface BookCompilationRepository extends JpaRepository<BookCompilation, Long> {
  @EntityGraph(value = "BookCompilation.detail", type = EntityGraph.EntityGraphType.FETCH)
  @NonNull
  List<BookCompilation> findAll();

  @EntityGraph(value = "BookCompilation.detail", type = EntityGraph.EntityGraphType.FETCH)
  @NonNull
  Optional<BookCompilation> findById(@NonNull Long id);

  Page<BookCompilation> findByNameContainingIgnoreCase(Pageable pageable, String search);
}
