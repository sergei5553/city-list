package com.yatsevich.citylist.api.boot.security;

public class UserAuthority {

  public static final String FULL_AUTHORITY = "FULL_ACCESS";
  public static final String READ_ONLY_AUTHORITY = "READ_ONLY_ACCESS";

  private static final String HAS_AUTHORITY_PREFIX = "hasAuthority('";
  private static final String HAS_AUTHORITY_SUFFIX = "')";

  public static final String HAS_ACCESS = HAS_AUTHORITY_PREFIX + READ_ONLY_AUTHORITY + HAS_AUTHORITY_SUFFIX;
  public static final String HAS_FULL_ACCESS = HAS_AUTHORITY_PREFIX + FULL_AUTHORITY + HAS_AUTHORITY_SUFFIX;

  private UserAuthority() {
  }
}
