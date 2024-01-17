package com.test.spring.appointment.model;

import com.test.spring.doctor.model.Doctor;
import com.test.spring.patient.model.Patient;
import com.test.spring.common.AppointmentReason;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class Appointment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @ManyToOne
    private Doctor doctor;
    @ManyToOne
    private Patient patient;
    @DateTimeFormat(pattern = "dd-MM-yyyy HH:mm")
    private LocalDateTime term;
    @Enumerated(EnumType.STRING)
    private AppointmentReason appointmentReason;
    private int appointmentTime;

}
