package com.test.spring.doctor;

import com.test.spring.doctor.model.Doctor;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.same;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class DoctorServiceTest {
    @InjectMocks
    private DoctorService doctorService;

    @Mock
    private DoctorRepository doctorRepository;

    @Test
    void testFindAll_ResultsInDoctorListBeingReturned() {
        //given
        List<Doctor> doctorsFromRepo = List.of(new Doctor(), new Doctor());
        when(doctorRepository.findAll()).thenReturn(doctorsFromRepo);
        //when
        List<Doctor> returned = doctorService.findAll();

        //then
        assertEquals(doctorsFromRepo,returned);
    }

    @Test
    void testDelete_ResultsInDoctorBeingDeleted() {
        int doctoriId = 1;

        doctorService.delete(doctoriId);

        verify(doctorRepository).deleteById(doctoriId);
    }
}