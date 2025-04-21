package kz.diploma.kitaphub.data.repository;

import kz.diploma.kitaphub.data.entity.BookLink;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookLinkRepository extends JpaRepository<BookLink, Long> {
}
