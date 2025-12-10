package com.healthcare.provider.controller;

import com.healthcare.provider.model.LevelOfTreatment;
import com.healthcare.provider.model.PatientAgeBracket;
import com.healthcare.provider.model.Practices;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/provider/options")
public class ProviderOptionsController {
  @GetMapping("/practices")
  public Practices[] getPractices() {
    return Practices.values();
  }

  @GetMapping("/treatment-levels")
  public LevelOfTreatment[] getTreatmentLevels() {
    return LevelOfTreatment.values();
  }

  @GetMapping("/age-brackets")
  public PatientAgeBracket[] getAgeBrackets() {
    return PatientAgeBracket.values();
  }
}
