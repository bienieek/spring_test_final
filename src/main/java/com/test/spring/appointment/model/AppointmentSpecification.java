package com.test.spring.appointment.model;

import com.test.spring.common.AppointmentReason;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.jpa.domain.Specification;
import jakarta.persistence.criteria.Predicate;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@Getter
@Setter
public class AppointmentSpecification {
    private  Integer doctorId;
    private Integer patientId;
    private AppointmentReason appointmentReason;
    private LocalDateTime dateFrom;
    private LocalDateTime dateTo;

    public static Specification<Appointment> getSpecification(Integer doctorId,
                                                              Integer patientId,
                                                              AppointmentReason appointmentReason,
                                                              LocalDateTime dateFrom,
                                                              LocalDateTime dateTo) {
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();
            if (doctorId != null) {
                predicates.add(criteriaBuilder.equal(root.get("doctor").get("id"),doctorId));
            }
            if(patientId != null) {
                predicates.add(criteriaBuilder.equal(root.get("patient").get("id"),patientId));
            }
            if(appointmentReason != null) {
                predicates.add(criteriaBuilder.equal(root.get("appointmentReason"),appointmentReason));
            }
            if(dateFrom != null) {
                predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("term"),dateFrom));
            }
            if(dateTo != null) {
                predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get("term"),dateTo));
            }
            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }
}
