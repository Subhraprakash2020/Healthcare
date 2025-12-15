package com.healthcare.provider.controller;

import com.healthcare.provider.model.ProviderSearchRequest;
import com.healthcare.provider.service.ProviderSearchService;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/healthcare/provider/search")
public class ProviderSearchController {
  @Autowired ProviderSearchService providerSearchService;

  @PostMapping("/searchBy")
  public ResponseEntity<?> searchProviders(@RequestBody ProviderSearchRequest request) {
    List<Map<String, Object>> results = providerSearchService.searchProviders(request);
    return ResponseEntity.ok(results);
  }
}
