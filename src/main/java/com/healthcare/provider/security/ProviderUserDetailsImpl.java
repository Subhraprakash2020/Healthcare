package com.healthcare.provider.security;

import com.healthcare.provider.model.Provider;
import com.healthcare.provider.model.ProviderPrincipal;
import com.healthcare.provider.repository.ProviderRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service("providerUserDetailsService")
public class ProviderUserDetailsImpl implements UserDetailsService {

  private final ProviderRepository providerRepository;

  public ProviderUserDetailsImpl(ProviderRepository providerRepository) {
    this.providerRepository = providerRepository;
  }

  @Override
  public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
    System.out.println("Loading provider by username: " + email);
    Provider provider = providerRepository.findByEmail(email).orElse(null);

    if (provider == null) {
      System.out.println("Provider not found with username: " + email);
      throw new UsernameNotFoundException("Provider not found with username: " + email);
    }

    return ProviderPrincipal.build(provider);
  }

  public static ProviderPrincipal build(Provider provider) {
    return ProviderPrincipal.build(provider);
  }
}
