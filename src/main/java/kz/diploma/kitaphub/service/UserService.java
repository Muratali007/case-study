package kz.diploma.kitaphub.service;

import java.util.Optional;
import kz.diploma.kitaphub.data.entity.User;
import kz.diploma.kitaphub.data.entity.UserBook;
import kz.diploma.kitaphub.data.repository.SocialLinkRepository;
import kz.diploma.kitaphub.data.repository.UserBookRepository;
import kz.diploma.kitaphub.data.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@Slf4j
public class UserService {
  private final UserRepository userRepository;

  private final SocialLinkRepository socialLinkRepository;

  private final UserBookRepository userBookRepository;

  public UserService(UserRepository userRepository,
                     SocialLinkRepository socialLinkRepository, UserBookRepository userBookRepository) {
    this.userRepository = userRepository;
    this.socialLinkRepository = socialLinkRepository;
    this.userBookRepository = userBookRepository;
  }

  public Optional<User> getUserById(Long id) {
    return userRepository.findById(id);
  }

  public Optional<User> getUserByUsername(String username) {
    return userRepository.findUserByUsername(username);
  }

  public User save(User user) {
    return userRepository.save(user);
  }

  public UserBook saveUserBook(UserBook userBook) {
    return userBookRepository.save(userBook);
  }

  public User updateUser(User user) {
    var socialLinkReq = user.getSocialLink();
    socialLinkRepository.save(socialLinkReq);

    return userRepository.save(user);
  }
}
