package com.test.spring.doctor;

import com.test.spring.doctor.model.Doctor;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;

import java.util.Optional;


public interface DoctorRepository extends JpaRepository<Doctor, Integer> {
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    Optional<Doctor> findWithLockingById(int id);
}
