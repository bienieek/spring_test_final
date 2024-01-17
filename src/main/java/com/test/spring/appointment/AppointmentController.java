package com.test.spring.appointment;

import com.test.spring.appointment.model.Appointment;
import com.test.spring.appointment.model.AppointmentCriteria;
import com.test.spring.doctor.DoctorService;
import com.test.spring.patient.PatientService;
import com.test.spring.common.AppointmentReason;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/appointments")
public class AppointmentController {
    private final AppointmentService appointmentService;
    private final DoctorService doctorService;
    private final PatientService patientService;

    @GetMapping
    public String getList(Model model, @ModelAttribute AppointmentCriteria criteria) {
        List<Appointment> appointmentList = appointmentService.getSortedAppointments(criteria);
        model.addAttribute("criteria",criteria);
        model.addAttribute("appointments", appointmentList);
        model.addAttribute("doctors",doctorService.findAll());
        model.addAttribute("patients",patientService.findAll());
        model.addAttribute("appointmentReasons",AppointmentReason.values());
        return "appointment/list";
    }
    @PostMapping("/create")
    public String create(Appointment appointment, @RequestParam Integer doctorId, @RequestParam Integer patientId){
        appointmentService.create(appointment,doctorId,patientId);
        return "redirect:/appointments";
    }
    @GetMapping("/create")
    public String getCreateForm(Model model){
        model.addAttribute("reasons", AppointmentReason.values());
        model.addAttribute("doctors",doctorService.findAll());
        model.addAttribute("patients",patientService.findAll());
        return "appointment/form";
    }
    @GetMapping("/delete")
    public String delete(@RequestParam Integer appointmentId){
        appointmentService.deleteAppointment(appointmentId);
        return "redirect:/appointments";
    }

}
