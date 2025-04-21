package kz.diploma.kitaphub.utils;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;

public class JwtUtils {
  public static String getUsername() {
    JwtAuthenticationToken jwtAuthenticationToken =
        (JwtAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
    return (String) jwtAuthenticationToken.getTokenAttributes().get("preferred_username");
  }
}
