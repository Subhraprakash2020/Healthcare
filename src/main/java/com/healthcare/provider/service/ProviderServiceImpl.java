package com.healthcare.provider.service;

import com.healthcare.patient.security.jwt.JwtUtils;
import com.healthcare.provider.model.Provider;
import com.healthcare.provider.model.ProviderPrincipal;
import com.healthcare.provider.model.Status;
import com.healthcare.provider.payload.request.LoginRequestProvider;
import com.healthcare.provider.repository.ProviderRepository;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class ProviderServiceImpl implements ProviderServices, UserDetailsService {

  @Autowired ProviderRepository providerRepository;
  @Autowired private AuthenticationManager authenticationManager;

  @Autowired
  //  private PasswordEncoder encoder;
  //  @Autowired
  private JwtUtils jwtUtils;

  private BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder(15);

  public void addProvider(Provider provider) {

    Provider nonNullProvider = Objects.requireNonNull(provider, "Provider cannot be null");
    nonNullProvider.setPassWord(passwordEncoder.encode(nonNullProvider.getPassWord()));
    providerRepository.save(nonNullProvider);
  }

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    Provider provider = providerRepository.findByEmail(username).orElse(null);
    if (provider == null) {
      throw new UsernameNotFoundException("Provider not found with email: " + username);
    }
    return ProviderPrincipal.build(provider);
  }

  public ResponseEntity<?> verify(LoginRequestProvider loginRequest) {

    Authentication authentication;

    try {
      authentication =
          authenticationManager.authenticate(
              new UsernamePasswordAuthenticationToken(
                  loginRequest.getEmail(), loginRequest.getPassWord()));
    } catch (AuthenticationException ex) {
      return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid provider credentials");
    }

    SecurityContextHolder.getContext().setAuthentication(authentication);

    Object principal = authentication.getPrincipal();

    if (!(principal instanceof ProviderPrincipal providerPrincipal)) {
      return ResponseEntity.status(HttpStatus.FORBIDDEN)
          .body("Access denied: Not a provider account");
    }

    if (!"PROVIDER".equals(providerPrincipal.getRole())) {
      return ResponseEntity.status(HttpStatus.FORBIDDEN)
          .body("Access denied: Provider role required");
    }

    String jwt = jwtUtils.generateJwtToken(authentication);

    return ResponseEntity.ok(
        Map.of(
            "token", jwt,
            "id", providerPrincipal.getId(),
            "email", providerPrincipal.getEmail(),
            "role", providerPrincipal.getRole()));
  }

  @Override
  public List<Provider> getAllProviders() {
    return providerRepository.findAll();
  }

  @Override
  public Provider getProviderById(Long id) {
    return providerRepository.findById(id).orElse(null);
  }

  @Override
  public Provider updateProvider(Long id, Provider providerDetails) {
    Provider provider = providerRepository.findById(id).orElse(null);
    if (provider != null) {
      if (providerDetails.getFirstName() != null) {
        provider.setFirstName(providerDetails.getFirstName());
      }
      if (providerDetails.getLastName() != null) {
        provider.setLastName(providerDetails.getLastName());
      }
      if (providerDetails.getPhone() != null) {
        provider.setPhone(providerDetails.getPhone());
      }
      if (providerDetails.getClinicAddress() != null) {
        provider.setClinicAddress(providerDetails.getClinicAddress());
      }
      if (providerDetails.getPassWord() != null && !providerDetails.getPassWord().isEmpty()) {
        provider.setPassWord(passwordEncoder.encode(providerDetails.getPassWord()));
      }
    }

    provider.setUpdatedAt(LocalDateTime.now());

    return providerRepository.save(provider);
  }

  @Override
  public Provider deleteProvider(Long id) {
    Provider provider =
        providerRepository
            .findById(id)
            .orElseThrow(() -> new RuntimeException("Provider not found"));
    provider.setStatus(Status.INACTIVE);
    return providerRepository.save(provider);
  }
}
