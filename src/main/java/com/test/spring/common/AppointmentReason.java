package com.test.spring.common;

import lombok.Getter;

@Getter
public enum AppointmentReason {
    ILLNESS("Choroba",30),
    PREVENTION("Profilaktyka", 20),
    TESTS("Badania", 15);
    private final String showValue;
    private final int appointmentTime;

    AppointmentReason(String showValue, int aTime) {
        this.showValue = showValue;
        this.appointmentTime = aTime;
    }
}
