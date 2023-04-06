package com.yatsevich.citylist.api.boot.security;

import org.springframework.boot.context.properties.ConfigurationProperties;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@ConfigurationProperties("security.basic")
public class BasicSecurityConfigurationProperties {

  private Credentials fullAccessUser;
  private Credentials readOnlyUser;

  @Getter
  @Setter
  @NoArgsConstructor
  public static class Credentials {
    private String username;
    private String password;
  }
}
