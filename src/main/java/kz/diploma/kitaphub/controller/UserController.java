package kz.diploma.kitaphub.controller;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import kz.diploma.kitaphub.data.entity.SocialLink;
import kz.diploma.kitaphub.data.entity.User;
import kz.diploma.kitaphub.data.entity.UserBook;
import kz.diploma.kitaphub.data.entity.UserBookStatus;
import kz.diploma.kitaphub.service.AuthService;
import kz.diploma.kitaphub.service.BookService;
import kz.diploma.kitaphub.service.KeycloakService;
import kz.diploma.kitaphub.service.SocialLinkService;
import kz.diploma.kitaphub.service.UserService;
import kz.diploma.kitaphub.utils.JwtUtils;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequestMapping("/user")
public class UserController {

  private final AuthService authService;
  private final UserService userService;
  private final SocialLinkService socialLinkService;

  private final BookService bookService;

  private final KeycloakService keycloakService;


  public UserController(AuthService authService, UserService userService,
                        SocialLinkService socialLinkService, BookService bookService,
                        KeycloakService keycloakService) {
    this.authService = authService;
    this.userService = userService;
    this.socialLinkService = socialLinkService;
    this.bookService = bookService;
    this.keycloakService = keycloakService;
  }

  @GetMapping("/getProfile")
  public User getProfile() {
    return authService.getCurrentUser();
  }

  @PostMapping
  public ResponseEntity<Long> addUserToDb(@RequestBody AddUserRequest request,
                                          Authentication authentication) {
    String userId = ((Jwt) authentication.getPrincipal()).getSubject();
    var keycloakUser = keycloakService.updateAddedToDb(userId);

    SocialLink socialLink = new SocialLink();
    socialLink.setInstagramLink(request.getInstagram());
    socialLink.setTelegramLink(request.getTelegram());
    var social = socialLinkService.save(socialLink);


    User user = new User();
    user.setUsername(keycloakUser.getUsername());
    user.setName(keycloakUser.getFirstName());
    user.setSurname(keycloakUser.getLastName());
    user.setEmail(keycloakUser.getEmail());
    user.setPhone(request.getPhone());
    user.setSocialLink(social);
    var createdUser = userService.save(user);

    for (String isbn : request.likedBooks) {
      UserBook userBook = new UserBook();
      userBook.setUser(createdUser);
      var book = bookService.getBookByIsbn(isbn).orElseThrow();
      userBook.setBook(book);
      userBook.setHave(false);
      userBook.setRating(5);
      userBook.setStatus(UserBookStatus.READ);
      userService.saveUserBook(userBook);
    }

    return new ResponseEntity<>(createdUser.getId(), HttpStatus.CREATED);
  }

  @PutMapping("/update")
  public ResponseEntity<Long> updateUser(@RequestBody User user) {

    var username = JwtUtils.getUsername();

    if (!username.equals(user.getUsername())) {
      return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    }

    var updateUser = userService.updateUser(user);

    return new ResponseEntity<>(updateUser.getId(), HttpStatus.CREATED);
  }

  @GetMapping("/{id}")
  public Optional<User> getUserById(@PathVariable Long id) {
    return userService.getUserById(id);
  }

  @GetMapping("/roles")
  public List<String> getUserRoles() {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    return authentication.getAuthorities().stream()
        .map(GrantedAuthority::getAuthority)
        .collect(Collectors.toList());
  }

  @Getter
  @Setter
  public static class AddUserRequest {
    private String phone;
    private String telegram;
    private String instagram;
    private List<String> likedBooks;
  }
}
