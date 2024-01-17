package com.test.spring.doctor.model;

import com.test.spring.appointment.model.Appointment;
import com.test.spring.patient.model.Patient;
import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class Doctor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String firstName;
    private String lastName;
    @OneToMany(mappedBy = "doctor")
    private Set<Patient> patients = new HashSet<>();

    @OneToMany(mappedBy = "doctor")
    private Set<Appointment> appointments = new HashSet<>();

    @Override
    public String toString() {
        return  firstName + " " + lastName;
    }
}
