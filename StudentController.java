package com.student.student.controller;

import com.student.student.entity.Student;
import com.student.student.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class StudentController {

    @Autowired
    private StudentRepository repo;

    // Dashboard
    @GetMapping("/dashboard")
    public String dashboard(Model model) {
        model.addAttribute("students", repo.findAll());
        model.addAttribute("total", repo.count());
        model.addAttribute("male", repo.countByGender("Male"));
        model.addAttribute("female", repo.countByGender("Female"));
        return "dashboard";
    }

    // Add Student
    @PostMapping("/add")
    public String addStudent(@RequestParam String name,
                             @RequestParam String email,
                             @RequestParam String course,
                             @RequestParam String gender,
                             @RequestParam(required = false) String dob) {

        Student s = new Student();
        s.setName(name);
        s.setEmail(email);
        s.setCourse(course);
        s.setGender(gender);

        if (dob != null && !dob.isEmpty()) {
            s.setDob(java.time.LocalDate.parse(dob));
        }

        repo.save(s);
        return "redirect:/dashboard";
    }

    // Delete
    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Long id) {
        repo.deleteById(id);
        return "redirect:/dashboard";
    }

    // Edit Page
    @GetMapping("/edit/{id}")
    public String editPage(@PathVariable Long id, Model model) {
        Student s = repo.findById(id).orElse(null);
        model.addAttribute("student", s);
        return "edit";
    }

    // Update
    @PostMapping("/update")
    public String updateStudent(@ModelAttribute Student s) {
        repo.save(s);
        return "redirect:/dashboard";
    }

    // 🔥 SEARCH
    @GetMapping("/search")
    public String search(@RequestParam String keyword, Model model) {

        java.util.List<Student> list;

        try {
            Long id = Long.parseLong(keyword);
            list = repo.findAll().stream()
                    .filter(s -> s.getId().equals(id))
                    .toList();
        } catch (Exception e) {
            list = repo.findByNameContaining(keyword);
        }

        model.addAttribute("students", list);
        model.addAttribute("total", list.size());
        model.addAttribute("male", list.stream().filter(s -> s.getGender().equals("Male")).count());
        model.addAttribute("female", list.stream().filter(s -> s.getGender().equals("Female")).count());

        return "dashboard";
    }
}