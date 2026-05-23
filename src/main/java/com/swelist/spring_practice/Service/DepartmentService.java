package com.swelist.spring_practice.Service;

import com.swelist.spring_practice.Repository.DepartmentRepository;
import com.swelist.spring_practice.entity.Department;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DepartmentService {

    private final DepartmentRepository departmentRepository;

    public Department findOrCreateDepartment(String departmentName) {
        return departmentRepository.findByName(departmentName)
                .orElseGet(() -> {
                    Department department = Department.builder()
                            .name(departmentName)
                            .build();

                    return departmentRepository.save(department);
                });
    }
}