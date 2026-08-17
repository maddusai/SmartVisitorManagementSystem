package com.traved.visitor.repository;

import com.traved.visitor.model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {

    // For login/email check if needed
    Optional<Employee> findByEmail(String email);
}
