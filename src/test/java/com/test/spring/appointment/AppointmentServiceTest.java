package com.test.spring.appointment;

import com.test.spring.appointment.model.Appointment;
import com.test.spring.appointment.model.AppointmentCriteria;
import com.test.spring.common.AppointmentReason;
import com.test.spring.doctor.model.Doctor;
import com.test.spring.exception.InvalidDateException;
import com.test.spring.exception.InvalidPersonException;
import com.test.spring.patient.model.Patient;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import com.test.spring.patient.PatientRepository;
import com.test.spring.doctor.DoctorRepository;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;

import static org.junit.jupiter.api.Assertions.*;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import static org.mockito.Mockito.*;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatExceptionOfType;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AppointmentServiceTest {

    @InjectMocks
    AppointmentService appointmentService;

    @Mock
    AppointmentRepository appointmentRepository;
    @Mock
    DoctorRepository doctorRepository;
    @Mock
    PatientRepository patientRepository;
    @Captor
    private ArgumentCaptor<Appointment> appointmentArgumentCaptor;

    @Test
    void testCrate_ResultsAppointmentIsSaved() {
        //given
        Doctor doctor = new Doctor();
        Patient patient = new Patient();
        AppointmentReason appointmentReason = AppointmentReason.PREVENTION;
        Appointment appToSave = Appointment.builder().appointmentReason(appointmentReason)
                .term(LocalDateTime.now().plusDays(2)).build();
        int doctorId = 1;
        int patientId = 10;
        when(doctorRepository.findWithLockingById(doctorId)).thenReturn(Optional.of(doctor));
        when(patientRepository.findById(patientId)).thenReturn(Optional.of(patient));

        //when
        appointmentService.create(appToSave,doctorId,patientId);

        //then
        verify(doctorRepository).findWithLockingById(doctorId);
        verify(patientRepository).findById(patientId);
        verify(appointmentRepository).save(appointmentArgumentCaptor.capture());
        Appointment appSaved = appointmentArgumentCaptor.getValue();

        assertEquals(doctor, appSaved.getDoctor());
        assertEquals(patient, appSaved.getPatient());
        assertEquals(appToSave.getAppointmentReason(), appSaved.getAppointmentReason());
        assertEquals(appToSave.getTerm(), appSaved.getTerm());
        assertEquals(appSaved,appToSave);
    }

    @Test
    void testCreate_DateIsInThePast_ResultsInvalidDateException() {
        //given
        Appointment appToTest = Appointment.builder().term(LocalDateTime.now().minusDays(2)).appointmentReason(AppointmentReason.TESTS).build();
        String exceptionMsg = "You must change appointmante date, selected is from the past";

        //when //then
        assertThatExceptionOfType(InvalidDateException.class)
                .isThrownBy(()-> appointmentService.create(appToTest,1,1))
                .withMessage(exceptionMsg);
    }
    @Test
    void testCreate_DoctorNotFound_ResultsInInvalidPersonException() {
        //given
        Appointment appToTest = Appointment.builder().appointmentReason(AppointmentReason.TESTS).term(LocalDateTime.now().plusDays(1)).build();
        String exceptionMsg = "Doctor not found";
        int doctorId = 1;
        when(doctorRepository.findWithLockingById(doctorId)).thenReturn(Optional.empty());

        //when //then
        assertThatExceptionOfType(InvalidPersonException.class)
                .isThrownBy(()-> appointmentService.create(appToTest,doctorId,1))
                .withMessage(exceptionMsg);
        verify(doctorRepository).findWithLockingById(doctorId);
    }
    @Test
    void testCreate_PatientNotFound_ResultsInInvalidPersonException() {
        //given
        Appointment appToTest = Appointment.builder().appointmentReason(AppointmentReason.TESTS).term(LocalDateTime.now().plusDays(2)).build();
        String exceptionMsg = "Patient not found";
        int doctorId = 1;
        int patientId = 1;
        when(doctorRepository.findWithLockingById(doctorId)).thenReturn(Optional.of(new Doctor()));
        when(patientRepository.findById(patientId)).thenReturn(Optional.empty());

        //when //then
        assertThatExceptionOfType(InvalidPersonException.class)
                .isThrownBy(()-> appointmentService.create(appToTest,doctorId,patientId))
                .withMessage(exceptionMsg);
        verify(doctorRepository).findWithLockingById(doctorId);
        verify(patientRepository).findById(patientId);
    }

    @Test
    void testCreate_DateIsBusy_ResultsInInvalidDateException() {
        //given
        LocalDateTime date = LocalDateTime.now().plusDays(1);
        AppointmentReason appointmentReason = AppointmentReason.TESTS;
        Appointment appToTest = Appointment.builder().appointmentReason(appointmentReason).term(date).build();
        String exceptionMsg = "You must change appointmante date, selected is busy";
        int doctorId = 1;
        Doctor doctor = new Doctor();
        Appointment appWrongDateApp = Appointment.builder().appointmentReason(appointmentReason).term(date)
                .appointmentTime(appointmentReason.getAppointmentTime()).build();
        when(appointmentRepository.findAllByDoctorAndTermAfterAndTermBefore(eq(doctor),any(LocalDateTime.class), any(LocalDateTime.class)))
                .thenReturn(List.of(appWrongDateApp));
        when(doctorRepository.findWithLockingById(doctorId)).thenReturn(Optional.of(doctor));

        //when //then
        assertThatExceptionOfType(InvalidDateException.class)
                .isThrownBy(() -> appointmentService.create(appToTest,doctorId,1))
                .withMessage(exceptionMsg);
        verify(doctorRepository).findWithLockingById(doctorId);
    }

    @Test
    void testGetSortedAppointments_ResultsInAppointmentsBeingReturned() {
        //given
        Appointment build1 = Appointment.builder().id(1).build();
        Appointment build2 = Appointment.builder().id(2).build();
        List<Appointment> appointmentsFromRepo = List.of(build1,build2);
        AppointmentCriteria criteria = new AppointmentCriteria();
        criteria.setSortField("id");
        criteria.setSortDirection("asc");
        when(appointmentRepository.findAll(any(Specification.class), any(Sort.class))).thenReturn(appointmentsFromRepo);
        //when
        List<Appointment> returned = appointmentService.getSortedAppointments(criteria);

        //then
        assertEquals(appointmentsFromRepo,returned);
        verify(appointmentRepository).findAll(any(Specification.class), any(Sort.class));
    }

    @Test
    void testGetSortedAppointments_WithNotRequiredVar_ResultsInAppointmentsBeingReturned() {
        //given
        AppointmentCriteria criteria = new AppointmentCriteria();
        criteria.setDoctorId(1);
        criteria.setPatientId(2);
        criteria.setAppointmentReason(AppointmentReason.TESTS);
        criteria.setDateFrom(LocalDateTime.now().plusDays(1));
        criteria.setDateTo(LocalDateTime.now().plusDays(2));
        criteria.setSortDirection("asc");
        criteria.setSortField("id");
        Appointment build1 = Appointment.builder().id(1).build();
        Appointment build2 = Appointment.builder().id(2).build();
        List<Appointment> appointmentsFromRepo = List.of(build1,build2);
        when(appointmentRepository.findAll(any(Specification.class), any(Sort.class))).thenReturn(appointmentsFromRepo);

        //when
        List<Appointment> returned = appointmentService.getSortedAppointments(criteria);

        //then
        assertEquals(appointmentsFromRepo, returned);
        verify(appointmentRepository).findAll(any(Specification.class),any(Sort.class));
    }

    @Test
    void testGetSortedAppointments_InvalidSortFieldName_ResultsInIllegalArgumentException() {
        //given
        AppointmentCriteria criteria = new AppointmentCriteria();
        criteria.setSortField("testing");
        criteria.setSortDirection("asc");
        String exceptionMsg = "Invalid sort field";

        //when //then
        assertThatExceptionOfType(IllegalArgumentException.class)
                .isThrownBy(() -> appointmentService.getSortedAppointments(criteria))
                .withMessage(exceptionMsg);

    }

    @Test
    void testGetSortedAppointments_InvalidSortDirectionName_ResultsInIllegalArgumentException() {
        //given
        AppointmentCriteria criteria = new AppointmentCriteria();
        criteria.setSortField("id");
        criteria.setSortDirection("testing");
        String exceptionMsg = "Invalid sort direction";

        //when //then
        assertThatExceptionOfType(IllegalArgumentException.class)
                .isThrownBy(() -> appointmentService.getSortedAppointments(criteria))
                .withMessage(exceptionMsg);
    }

    @Test
    void testGetSortedAppointments_DateFromIsBeforeDateTo_ResultsInInvalidDateException() {
        //given
        AppointmentCriteria criteria = new AppointmentCriteria();
        criteria.setDateFrom(LocalDateTime.now().plusDays(1));
        criteria.setDateTo(LocalDateTime.now().minusDays(1));
        criteria.setSortField("id");
        criteria.setSortDirection("asc");
        String exceptionMsg = "Date from, must be before date to";

        //when //then
        assertThatExceptionOfType(InvalidDateException.class)
                .isThrownBy(() -> appointmentService.getSortedAppointments(criteria))
                .withMessage(exceptionMsg);
    }

}