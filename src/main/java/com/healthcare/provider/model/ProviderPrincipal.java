package com.healthcare.provider.model;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

public class ProviderPrincipal implements UserDetails {

  private Long id;
  private String email;
  private String passWord;
  private String role;
  private Collection<? extends GrantedAuthority> authorities;

  private ProviderPrincipal(
      Long id,
      String username,
      String email,
      String passWord,
      String role,
      Collection<? extends GrantedAuthority> authorities) {
    this.id = id;
    this.email = email;
    this.passWord = passWord;
    this.authorities = authorities;
  }

  // Overloaded constructor to accept String IDs (e.g., provider.getId() returns String)
  private ProviderPrincipal(
      String id,
      String username,
      String email,
      String passWord,
      String role,
      Collection<? extends GrantedAuthority> authorities) {
    if (id != null) {
      try {
        this.id = Long.parseLong(id);
      } catch (NumberFormatException e) {
        this.id = null;
      }
    } else {
      this.id = null;
    }
    this.email = email;
    this.role = role;
    this.passWord = passWord;
    this.authorities = authorities;
  }

  public static ProviderPrincipal build(Provider provider) {
    List<GrantedAuthority> authorities =
        Collections.singletonList(new SimpleGrantedAuthority("ROLE_PROVIDER"));

    return new ProviderPrincipal(
        provider.getId(),
        provider.getUserId(),
        provider.getEmail(),
        provider.getRole(),
        provider.getPassWord(),
        authorities);
  }

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return authorities; // ✅ return stored authorities
  }

  @Override
  public String getPassword() {
    return passWord; // ✅ safe
  }

  @Override
  public String getUsername() {
    return email; // ✅ safe
  }

  public Long getId() {
    return id;
  }

  public String getEmail() {
    return email;
  }

  public String getRole() {
    return role;
  }

  @Override
  public boolean isAccountNonExpired() {
    return true;
  }

  @Override
  public boolean isAccountNonLocked() {
    return true;
  }

  @Override
  public boolean isCredentialsNonExpired() {
    return true;
  }

  @Override
  public boolean isEnabled() {
    return true;
  }
}
