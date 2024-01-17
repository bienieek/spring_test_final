package com.test.spring.doctor;

import com.test.spring.doctor.model.Doctor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor
@RequestMapping("/doctors")
public class DoctorController {

    private final DoctorService doctorService;

    @GetMapping
    public String getList(Model model) {
        model.addAttribute("doctors",doctorService.findAll());
        return "doctor/list";
    }
    @GetMapping("/create")
    public String getCreateForm(){
        return "doctor/form";
    }
    @PostMapping("/create")
    public String create(Doctor doctor){
        doctorService.create(doctor);
        return "redirect:/doctors";
    }
    @GetMapping("/delete")
    public String delete(@RequestParam Integer doctorId){
        doctorService.delete(doctorId);
        return "redirect:/patients";
    }

}
