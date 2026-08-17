package com.traved.visitor.repository;

import com.traved.visitor.model.VisitLog;
import com.traved.visitor.model.Employee;
import com.traved.visitor.model.Visitor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface VisitLogRepository extends JpaRepository<VisitLog, Long> {

    List<VisitLog> findByVisitor(Visitor visitor);

    List<VisitLog> findByEmployee(Employee employee);

    // Optional: filter by today’s date (for dashboard)
    List<VisitLog> findByCheckInTimeBetween(LocalDate start, LocalDate end);
}
