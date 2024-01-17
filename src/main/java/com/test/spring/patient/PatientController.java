package com.test.spring.patient;

import com.test.spring.doctor.DoctorService;
import com.test.spring.patient.model.Patient;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RequestMapping("/patients")
@Controller
@RequiredArgsConstructor
public class PatientController {
    private final PatientService patientService;
    private final DoctorService doctorService;
    @GetMapping
    public String getList(Model model) {
        model.addAttribute("patients",patientService.findAll());
        return "patient/list";
    }
    @GetMapping("/create")
    public String getCreateForm(Model model){
        model.addAttribute("doctors", doctorService.findAll());
        return "patient/form";
    }
    @PostMapping("/create")
    public String create(Patient patient, @RequestParam Integer doctorId){
        patientService.create(patient,doctorId);
        return "redirect:/patients";
    }
    @GetMapping("/delete")
    public String delete(@RequestParam Integer patientId){
        patientService.delete(patientId);
        return "redirect:/patients";
    }
}
