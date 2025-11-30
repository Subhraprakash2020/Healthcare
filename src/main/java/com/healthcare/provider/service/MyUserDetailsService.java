package com.healthcare.provider.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.healthcare.patient.exception.ResourceNotFoundException;
import com.healthcare.provider.model.Provider;
import com.healthcare.provider.model.ProviderPrincipal;
import com.healthcare.provider.repository.ProviderRepository;

@Service
public class MyUserDetailsService implements UserDetailsService {


    @Autowired
    private ProviderRepository providerRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Provider provider = (Provider) providerRepository.findByUsername(username);
        

        if (provider == null) {
            System.out.println("User not found with username: " + username);
            throw new ResourceNotFoundException("User not found with username: " + username);
        }
        return new ProviderPrincipal(provider);
    }

}
