package com.swelist.spring_practice.controller;

import com.swelist.spring_practice.Service.StudentService;
import com.swelist.spring_practice.dto.StudentRequestDTO;
import com.swelist.spring_practice.dto.StudentResponse;
import com.swelist.spring_practice.entity.Student;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/students")
public class StudentController {
    private final StudentService studentService;

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteStudentById(@PathVariable Long id) {
        studentService.DeleteStudent(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();

    }

    @GetMapping
    public ResponseEntity<Page<StudentResponse>> getAllStudents(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "asc") String direction
    ) {
        Page<Student> students = studentService.findAllStudents( size, page,  direction, sortBy);

        Page<StudentResponse> studentResponses = students.map(this::StudentToResponseDTO);

        return ResponseEntity.ok(studentResponses);
    }

    @GetMapping("/{id}")
    public ResponseEntity<StudentResponse> getStudentById(@PathVariable Long id) {
        Student student = studentService.findStudentById(id);
        return ResponseEntity.ok().body(StudentToResponseDTO(student));

    }

    @GetMapping("/level/{level}")
    public ResponseEntity<List<StudentResponse>> getStudentByLevel(@PathVariable Integer level) {
        List<Student> student = studentService.findStudentByLevel(level);
        List<StudentResponse> studentResponses = new ArrayList<>();
        for (Student stud : student){
            studentResponses.add(StudentToResponseDTO(stud));

        }
        return ResponseEntity.status(HttpStatus.OK).body(studentResponses);
    }

    @GetMapping("/department/{department}")
    public ResponseEntity<List<StudentResponse>> getStudentByDepartment(@PathVariable String department) {
        List<Student> students = studentService.findStudentByDepartment(department);
        List<StudentResponse> studentResponses = new ArrayList<>();
        for (Student stud : students){
            studentResponses.add(StudentToResponseDTO(stud));
        }
        return ResponseEntity.status(HttpStatus.OK).body(studentResponses);
    }



    @PostMapping
    public ResponseEntity<StudentResponse> createStudent(@Valid @RequestBody StudentRequestDTO dto) {
        Student student1 = studentService.createStudent(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(StudentToResponseDTO(student1));
    }

    @PutMapping("/{id}")
    public ResponseEntity<StudentResponse> updateStudentById( @PathVariable Long id,@Valid @RequestBody StudentRequestDTO dto) {
        Student student = studentService.UpdateStudent(id, dto);
        return ResponseEntity.ok().body(StudentToResponseDTO(student));
    }



    private StudentResponse StudentToResponseDTO(Student  student) {
         return StudentResponse.builder()
                .firstName(student.getFirstName())
                .lastName(student.getLastName())
                .email(student.getEmail())
                .department(student.getDepartment())
                .level(student.getLevel())
                .createdDate(student.getCreatedAt())
                .lastModifiedDate(student.getUpdatedAt())
                .build();
    }


}
