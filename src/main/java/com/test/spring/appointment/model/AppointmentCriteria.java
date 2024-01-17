package com.test.spring.appointment.model;

import com.test.spring.common.AppointmentReason;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

@Getter
@Setter
public class AppointmentCriteria {
    private Integer doctorId;
    private Integer patientId;
    private AppointmentReason appointmentReason;
    @DateTimeFormat(pattern = "dd-MM-yyyy HH:mm")
    private LocalDateTime dateFrom;
    @DateTimeFormat(pattern = "dd-MM-yyyy HH:mm")
    private LocalDateTime dateTo;
    private String sortField = "id";
    private String sortDirection = "asc";

}
