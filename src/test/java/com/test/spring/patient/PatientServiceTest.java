package com.test.spring.patient;

import com.test.spring.patient.model.Patient;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PatientServiceTest {
    @InjectMocks
    private PatientService patientService;
    @Mock
    private PatientRepository patientRepository;

    @Test
    void testFindAll_ResultsInPatientListBeingReturned() {
        List<Patient> patientsFromRepo = List.of(new Patient(), new Patient());
        when(patientRepository.findAll()).thenReturn(patientsFromRepo);

        List<Patient> returned = patientService.findAll();

        assertEquals(patientsFromRepo,returned);
    }

    @Test
    void testDelete_ResultsInPatientBeingDeleted() {
        int patientId = 1;
         patientService.delete(patientId);
         verify(patientRepository).deleteById(patientId);
    }
}