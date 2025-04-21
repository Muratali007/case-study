package kz.diploma.kitaphub.service;

import kz.diploma.kitaphub.data.entity.User;
import kz.diploma.kitaphub.data.repository.UserRepository;
import kz.diploma.kitaphub.utils.JwtUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@Slf4j
public class AuthService {
  private final UserRepository userRepository;


  public AuthService(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  public User getCurrentUser() {
    return userRepository.findUserByUsername(JwtUtils.getUsername()).orElseThrow();
  }
}
