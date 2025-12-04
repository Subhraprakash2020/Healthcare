package com.healthcare.admin.security.services;

import com.healthcare.admin.model.Admin;
import java.util.Collection;
import java.util.List;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

public class AdminUserDetailsImpl implements UserDetails {

  private Long id;
  private String username;
  private String email;
  private String password;

  private Collection<? extends GrantedAuthority> authorities;

  private AdminUserDetailsImpl(
      Long id,
      String username,
      String email,
      String password,
      Collection<? extends GrantedAuthority> authorities) {
    this.id = id;
    this.username = username;
    this.email = email;
    this.password = password;
    this.authorities = authorities;
  }

  public static AdminUserDetailsImpl build(Admin admin) {
    // Take role from DB and convert into Spring role
    GrantedAuthority authority =
        new SimpleGrantedAuthority("ROLE_" + admin.getRole()); // Example: ROLE_ADMIN

    return new AdminUserDetailsImpl(
        admin.getId(),
        admin.getUsername(),
        admin.getEmail(),
        admin.getPassword(),
        List.of(authority));
  }

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return authorities;
  }

  @Override
  public String getPassword() {
    return password;
  }

  @Override
  public String getUsername() {
    return email; // IMPORTANT: use email as username
  }

  public Long getId() {
    return id;
  }

  public String getEmail() {
    return email;
  }
}
