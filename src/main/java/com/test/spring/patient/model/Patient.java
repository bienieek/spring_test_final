package com.test.spring.patient.model;

import com.test.spring.appointment.model.Appointment;
import com.test.spring.doctor.model.Doctor;
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
public class Patient {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String firstName;
    private String lastName;

    @ManyToOne
    private Doctor doctor;

    @OneToMany(mappedBy = "patient")
    private Set<Appointment> appointments = new HashSet<>();

    @Override
    public String toString() {
        return firstName +" "+lastName;
    }
}
