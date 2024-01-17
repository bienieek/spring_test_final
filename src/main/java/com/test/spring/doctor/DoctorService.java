package com.test.spring.doctor;

import com.test.spring.doctor.model.Doctor;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class DoctorService {
    private final DoctorRepository doctorRepository;

    public List<Doctor> findAll() {
        return doctorRepository.findAll();
    }
    public void create(Doctor doctor) {
        doctorRepository.save(doctor);
    }

    public void delete(Integer doctorId) {
        doctorRepository.deleteById(doctorId);
    }
}
