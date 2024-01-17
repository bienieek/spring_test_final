package com.test.spring.appointment;

import com.test.spring.appointment.model.Appointment;
import com.test.spring.doctor.model.Doctor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.time.LocalDateTime;
import java.util.List;


public interface AppointmentRepository extends JpaRepository<Appointment, Integer>, JpaSpecificationExecutor<Appointment> {

    List<Appointment> findAllByDoctorAndTermAfterAndTermBefore(Doctor doctor, LocalDateTime from, LocalDateTime to);
}
