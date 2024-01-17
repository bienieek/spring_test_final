package com.test.spring.patient;

import com.test.spring.patient.model.Patient;
import org.springframework.data.jpa.repository.JpaRepository;


public interface PatientRepository extends JpaRepository<Patient, Integer> {
}
