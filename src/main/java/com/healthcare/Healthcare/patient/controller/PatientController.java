package com.healthcare.Healthcare.patient.controller;

import com.healthcare.Healthcare.patient.model.Patient;
import com.healthcare.Healthcare.patient.repository.PatientRepository;
import com.healthcare.Healthcare.patient.service.SequenceGeneratorService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/healthcare")
public class PatientController {
  @Autowired private PatientRepository patientRepository;

  @Autowired private SequenceGeneratorService sequenceGeneratorService;

  // Patient Controller
  @PostMapping("/patient")
  public Patient createPatient(@RequestBody Patient patient) {
    patient.setId(sequenceGeneratorService.generateSequence(Patient.SEQUENCE_NAME));
    return patientRepository.save(patient);
  }

  // Get All Patients
  @GetMapping("/patients")
  public List<Patient> getAllPatients() {
    return patientRepository.findAll();
  }
}
