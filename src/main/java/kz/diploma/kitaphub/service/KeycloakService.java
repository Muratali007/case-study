package kz.diploma.kitaphub.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.stereotype.Service;

@Service
public class KeycloakService {
  private final Keycloak keycloak;

  public KeycloakService(Keycloak keycloak) {
    this.keycloak = keycloak;
  }

  public UserRepresentation updateAddedToDb(String userId) {
    UserRepresentation user = keycloak.realm("kitaphub").users()
        .get(userId).toRepresentation();

    Map<String, List<String>> userAttributes = new HashMap<>();
    userAttributes.put("dbAdded", List.of("true"));

    user.setAttributes(userAttributes);

    keycloak.realm("kitaphub").users().get(userId).update(user);
    return user;
  }
}
