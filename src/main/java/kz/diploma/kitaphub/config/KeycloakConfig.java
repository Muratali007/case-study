package kz.diploma.kitaphub.config;

import org.keycloak.OAuth2Constants;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.KeycloakBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class KeycloakConfig {
  @Bean
  Keycloak keycloak() {
    return KeycloakBuilder.builder()
        .serverUrl("http://13.53.171.71:9091")
        .realm("master")
        .clientId("admin-cli")
        .grantType(OAuth2Constants.PASSWORD)
        .username("admin")
        .password("admin")
        .build();
  }
}
