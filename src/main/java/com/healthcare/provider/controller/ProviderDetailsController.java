package com.healthcare.provider.controller;

import com.healthcare.provider.payload.request.ProviderDetailsRequest;
import com.healthcare.provider.service.ProviderDetailsService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("healthcare/providers/details")
public class ProviderDetailsController {

    @Autowired
    private ProviderDetailsService providerDetailsService;

    @PostMapping("/add")
    public ResponseEntity<?> addDetails(@RequestBody ProviderDetailsRequest request) {
        return providerDetailsService.addProviderDetails(request);
    }

    @GetMapping("/{providerId}")
    public ResponseEntity<?> getProviderDetails(@PathVariable Long providerId) {
        return providerDetailsService.getProviderDetailsByProviderId(providerId);
    }
}