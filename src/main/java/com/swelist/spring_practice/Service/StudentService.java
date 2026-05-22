package com.swelist.spring_practice.Service;

import com.swelist.spring_practice.Repository.StudentRepository;
import com.swelist.spring_practice.dto.StudentRequestDTO;
import com.swelist.spring_practice.entity.Student;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
@Service
@RequiredArgsConstructor
public class StudentService {

    private final StudentRepository studentRepository;

    public Student createStudent(StudentRequestDTO dto) {
        Optional<Student> existingStudent = studentRepository.findByEmail(dto.getEmail());

        if (existingStudent.isPresent()) {
            throw new RuntimeException("Student already exists");
        }

        Student student = Student.builder()
                .firstName(dto.getFirstName())
                .lastName(dto.getLastName())
                .email(dto.getEmail())
                .department(dto.getDepartment())
                .level(dto.getLevel())
                .createdAt(Instant.now())
                .updatedAt(Instant.now())
                .build();

        return studentRepository.save(student);
    }

    public List<Student> findAllStudents(){

       return  studentRepository.findAll();
    }

    public Student findStudentById(Long id){
        return studentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Student not found"));

    }

    public List<Student> findStudentByDepartment(String Department){
        return studentRepository.findByDepartment(Department);
    }

    public List<Student> findStudentByLevel(Integer Level){
        return studentRepository.findByLevel(Level);
    }

    public Student UpdateStudent(Long Id, StudentRequestDTO dto){
        Student student = findStudentById(Id);

        student.setUpdatedAt(Instant.now());

        if (student.getLevel() == dto.getLevel()){
            throw new  RuntimeException("Student level the same");
        } else if (student.getDepartment().equals(dto.getDepartment())) {
            throw new  RuntimeException("Student department the same");

        }
        student.setLevel(dto.getLevel());
        student.setFirstName(dto.getFirstName());
        student.setLastName(dto.getLastName());
        student.setEmail(dto.getEmail());
        student.setDepartment(dto.getDepartment());
        return studentRepository.save(student);
    }

    public void  DeleteStudent(Long Id){
        Student student = findStudentById(Id);
        studentRepository.delete(student);
    }
}


