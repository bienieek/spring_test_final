package com.test.spring.appointment;

import com.test.spring.appointment.model.Appointment;
import com.test.spring.appointment.model.AppointmentCriteria;
import com.test.spring.appointment.model.AppointmentSpecification;
import com.test.spring.doctor.DoctorRepository;
import com.test.spring.doctor.model.Doctor;
import com.test.spring.exception.InvalidPersonException;
import com.test.spring.patient.model.Patient;
import com.test.spring.patient.PatientRepository;
import com.test.spring.exception.InvalidDateException;
import com.test.spring.exception.InvalidSortException;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

@Service
@AllArgsConstructor
public class AppointmentService {
    private final AppointmentRepository appointmentRepository;
    private final DoctorRepository doctorRepository;
    private final PatientRepository patientRepository;

    public List<Appointment> getSortedAppointments(AppointmentCriteria criteria) {
        String sortField = criteria.getSortField();
        String sortDirection = criteria.getSortDirection();
        LocalDateTime dateFrom = criteria.getDateFrom();
        LocalDateTime dateTo = criteria.getDateTo();
        isSortingCorrect(sortField,sortDirection,dateFrom,dateTo);
        Sort sort = sortDirection.equals("asc") ? Sort.by(sortField).ascending() : Sort.by(sortField).descending();
        Specification<Appointment> specification = AppointmentSpecification.getSpecification(
                criteria.getDoctorId(), criteria.getPatientId(),criteria.getAppointmentReason(),dateFrom,dateTo );

        return appointmentRepository.findAll(specification,sort);
    }

    @Transactional
    public void create(Appointment appointment, int doctorId, int patientId){
        LocalDateTime term = appointment.getTerm();
        int appointmentTime = appointment.getAppointmentReason().getAppointmentTime();
        if(term.isBefore(LocalDateTime.now())) {
            throw new InvalidDateException("You must change appointmante date, selected is from the past");
        }
        Doctor doctor = doctorRepository.findWithLockingById(doctorId)
                        .orElseThrow(()-> new InvalidPersonException("Doctor not found"));
        if(!isAppointmentAvailable(doctor,term,appointmentTime)) {
            throw new InvalidDateException("You must change appointmante date, selected is busy");
        }
        Patient patient = patientRepository.findById(patientId)
                .orElseThrow(()-> new InvalidPersonException("Patient not found"));
        appointment.setDoctor(doctor);
        appointment.setPatient(patient);
        appointmentRepository.save(appointment);
    };

    private boolean isAppointmentAvailable(Doctor doctor, LocalDateTime appointmentStart, int appointmentTime) {
        LocalDateTime appointmentEnd = appointmentStart.plusMinutes(appointmentTime);
        List<Appointment> appointmentsByDoctor = appointmentRepository.
                findAllByDoctorAndTermAfterAndTermBefore(doctor,appointmentStart.minusHours(1),appointmentStart.plusHours(1));
        System.out.println(appointmentsByDoctor);
        for ( Appointment appointment : appointmentsByDoctor) {
            LocalDateTime aStart = appointment.getTerm();
            LocalDateTime aEnd = aStart.plusMinutes(appointment.getAppointmentTime());
            if (aStart.isBefore(appointmentEnd) && aEnd.isAfter(appointmentStart)) {
                return false;
            }
        }
        return true;
    }
    public void deleteAppointment(Integer id){
        appointmentRepository.deleteById(id);
    }

    private void isSortingCorrect(String sortField, String sortDirection, LocalDateTime dateFrom, LocalDateTime dateTo){
        if (sortField.trim().isEmpty() || !Arrays.asList("id", "doctor.lastName", "patient.lastName", "type", "term", "appointmentTime").contains(sortField)) {
            throw new InvalidSortException("Invalid sort field");
        }
        if (sortDirection.trim().isEmpty() || !Arrays.asList("asc", "desc").contains(sortDirection)) {
            throw new InvalidSortException("Invalid sort direction");
        }
        if (dateFrom != null && dateTo != null  && dateFrom.isAfter(dateTo)) {
            throw new InvalidDateException("Date from, must be before date to");
        }
    }
}
