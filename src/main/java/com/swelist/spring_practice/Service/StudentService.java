package com.swelist.spring_practice.Service;

import com.swelist.spring_practice.Repository.StudentRepository;
import com.swelist.spring_practice.dto.StudentRequestDTO;
import com.swelist.spring_practice.entity.Department;
import com.swelist.spring_practice.entity.Student;
import com.swelist.spring_practice.exceptionhandler.DuplicateStudentException;
import com.swelist.spring_practice.exceptionhandler.StudentNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
@Service
@RequiredArgsConstructor
public class StudentService {

    private final StudentRepository studentRepository;
    private final DepartmentService departmentService;

    public Student createStudent(StudentRequestDTO dto) {
        Optional<Student> existingStudent = studentRepository.findByEmail(dto.getEmail());

        if (existingStudent.isPresent()) {
            throw new DuplicateStudentException("Student already exists");
        }

        Department department = departmentService.findOrCreateDepartment(dto.getDepartment());

        Student student = Student.builder()
                .firstName(dto.getFirstName())
                .lastName(dto.getLastName())
                .email(dto.getEmail())
                .department(department)
                .level(dto.getLevel())
                .createdAt(Instant.now())
                .updatedAt(Instant.now())
                .build();

        return studentRepository.save(student);
    }

    public Page<Student> findAllStudents(int size, int page, String direction, String sortBy){
        Sort sort = direction.equalsIgnoreCase("desc")
                ? Sort.by(sortBy).descending()
                : Sort.by(sortBy).ascending();

        Pageable pageable = PageRequest.of(page, size, sort);
        return studentRepository.findAll(pageable);
    }

    public Student findStudentById(Long id){
        return studentRepository.findById(id)
                .orElseThrow(() -> new StudentNotFoundException("Student not found"));

    }

    public List<Student> findStudentByDepartment(String Department){
        return studentRepository.findByDepartment(Department);
    }

    public List<Student> findStudentByLevel(Integer Level){
        return studentRepository.findByLevel(Level);
    }

    public Student UpdateStudent(Long Id, StudentRequestDTO dto) {
        Student student = findStudentById(Id);

        Optional<Student> existingStudentWithEmail = studentRepository.findByEmail(dto.getEmail());

        if (existingStudentWithEmail.isPresent() &&
                !existingStudentWithEmail.get().getId().equals(Id)) {
            throw new DuplicateStudentException("Email already belongs to another student");
        }

        Department department = departmentService.findOrCreateDepartment(dto.getDepartment());

        student.setUpdatedAt(Instant.now());
        student.setLevel(dto.getLevel());
        student.setFirstName(dto.getFirstName());
        student.setLastName(dto.getLastName());
        student.setEmail(dto.getEmail());
        student.setDepartment(department);

        return studentRepository.save(student);
    }

    public void  DeleteStudent(Long Id){
        Student student = findStudentById(Id);
        studentRepository.delete(student);
    }
}


