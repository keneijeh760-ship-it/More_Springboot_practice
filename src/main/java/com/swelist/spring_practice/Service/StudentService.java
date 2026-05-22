package com.swelist.spring_practice.Service;

import com.swelist.spring_practice.Repository.StudentRepository;
import com.swelist.spring_practice.dto.StudentRequestDTO;
import com.swelist.spring_practice.entity.Student;
import lombok.RequiredArgsConstructor;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
public class StudentService {

    private final StudentRepository studentRepository;

    public Student createStudent (StudentRequestDTO dto){
        Optional<Student> student = studentRepository.findByEmail(dto.getEmail());

        if (student.isPresent()){
            throw new RuntimeException("Student already exists");
        }

        Student saved = student.get();

        return studentRepository.save(saved);
    }

    public List<Student> findAllStudents(){

       return  studentRepository.findAll();
    }

    public Student findStudentById(Long id){
        return studentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Student not found"));

    }

    public Student UpdateStudent(Long Id, StudentRequestDTO dto){
        Student student = findStudentById(Id);

        student.setUpdatedAt(Instant.now());

        if (student.getLevel() == dto.getLevel()){
            throw new  RuntimeException("Student level the same");
        } else if (student.getDepartment() == dto.getDepartment()) {
            throw new  RuntimeException("Student department the same");

        }
        return studentRepository.save(student);
    }

    public void  DeleteStudent(Long Id){
        Student student = findStudentById(Id);
        studentRepository.delete(student);
    }
}


