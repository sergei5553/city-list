package com.yatsevich.citylist.api.adapter.in.service.auth;

import com.yatsevich.citylist.api.adapter.in.UserContextController;
import com.yatsevich.citylist.api.adapter.in.service.auth.dto.AuthResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.security.core.GrantedAuthority;

import java.util.stream.Collectors;

@Slf4j
@RestController
public class UserContextApiService implements UserContextController {

  @Override
  public ResponseEntity<AuthResponse> getContext() {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

    AuthResponse authResponse = new AuthResponse(null, null);
    if (authentication != null && authentication.isAuthenticated()) {
      authResponse = createAuthResponse();
    }

    return authResponse.name() != null ? ResponseEntity.ok(authResponse)
        : ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
  }

  private AuthResponse createAuthResponse() {
    Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    if (principal instanceof UserDetails userDetails) {
      return new AuthResponse(((UserDetails) principal).getUsername(),
          userDetails.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.joining(",")));
    } else {
      return new AuthResponse(null, null);
    }
  }
}
