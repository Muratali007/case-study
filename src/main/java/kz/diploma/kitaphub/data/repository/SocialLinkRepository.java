package kz.diploma.kitaphub.data.repository;

import kz.diploma.kitaphub.data.entity.SocialLink;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional(propagation = Propagation.MANDATORY)
public interface SocialLinkRepository extends JpaRepository<SocialLink, Long> {
}
