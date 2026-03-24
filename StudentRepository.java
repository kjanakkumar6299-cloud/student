package com.student.student.repository;
import java.util.List;

import com.student.student.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentRepository extends JpaRepository<Student, Long> {

    long countByGender(String gender);

    List<Student> findByNameContaining(String name);
}