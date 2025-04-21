package kz.diploma.kitaphub.service;

import java.util.List;
import kz.diploma.kitaphub.data.entity.SocialLink;
import kz.diploma.kitaphub.data.repository.SocialLinkRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@Slf4j
public class SocialLinkService {
  private final SocialLinkRepository socialLinkRepository;

  public SocialLinkService(SocialLinkRepository socialLinkRepository) {
    this.socialLinkRepository = socialLinkRepository;
  }

  public SocialLink save(SocialLink socialLink) {
    return socialLinkRepository.save(socialLink);
  }
}
