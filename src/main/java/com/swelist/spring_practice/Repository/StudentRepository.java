package com.swelist.spring_practice.Repository;

import com.swelist.spring_practice.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface StudentRepository extends JpaRepository<Student,Long> {

    List<Student> findByDepartment(String department);

    List<Student> findByLevel(Integer level);

}
