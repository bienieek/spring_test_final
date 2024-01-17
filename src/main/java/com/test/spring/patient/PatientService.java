package com.test.spring.patient;

import com.test.spring.doctor.DoctorRepository;
import com.test.spring.doctor.model.Doctor;
import com.test.spring.patient.model.Patient;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class PatientService {
    private final PatientRepository patientRepository;
    private final DoctorRepository doctorRepository;

    public List<Patient> findAll() {
        return patientRepository.findAll();
    }
    public void create(Patient patient, int doctorId) {
        // zrob walidacje danych z walsnymi wyajtkami
        Doctor doctor = doctorRepository.findById(doctorId)
                        .orElseThrow();
        patient.setDoctor(doctor);
        patientRepository.save(patient);
    }

    public void delete(Integer patientId) {
        patientRepository.deleteById(patientId);
    }
}
