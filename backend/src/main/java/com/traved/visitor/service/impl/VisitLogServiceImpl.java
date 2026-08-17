package com.traved.visitor.service.impl;

import com.traved.visitor.dto.VisitLogDTO;
import com.traved.visitor.model.Employee;
import com.traved.visitor.model.Visitor;
import com.traved.visitor.model.VisitLog;
import com.traved.visitor.repository.EmployeeRepository;
import com.traved.visitor.repository.VisitLogRepository;
import com.traved.visitor.repository.VisitorRepository;
import com.traved.visitor.service.VisitLogService;
import com.traved.visitor.util.QRCodeGenerator;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class VisitLogServiceImpl implements VisitLogService {

    private final VisitLogRepository visitLogRepository;
    private final VisitorRepository visitorRepository;
    private final EmployeeRepository employeeRepository;
    private final JavaMailSender mailSender;

    @Override
    public VisitLog checkIn(Long visitorId, Long employeeId) {
        Visitor visitor = visitorRepository.findById(visitorId)
                .orElseThrow(() -> new RuntimeException("Visitor not found"));
        Employee employee = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new RuntimeException("Employee not found"));

        VisitLog log = VisitLog.builder()
                .visitor(visitor)
                .employee(employee)
                .checkInTime(LocalDateTime.now())
                .status("Checked-In")
                .build();

        VisitLog savedLog = visitLogRepository.save(log);

        // === QR Code Generation ===
        try {
            String qrText = "Visitor: " + visitor.getName() + "\nPurpose: " + visitor.getPurpose() +
                    "\nHost: " + employee.getName() + "\nVisit ID: " + savedLog.getVisitId();
            String filePath = "qrcodes/visit_" + savedLog.getVisitId() + ".png";
            QRCodeGenerator.generateQRCodeImageToFile(qrText, 300, 300, filePath);
        } catch (Exception e) {
            System.err.println("Failed to generate QR code: " + e.getMessage());
        }

        // === Email Notification ===
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setTo(employee.getEmail());
            message.setSubject("Visitor Check-In Alert");
            message.setText(
                    "Hello " + employee.getName() + ",\n\n" +
                            "Visitor " + visitor.getName() + " has checked in.\n" +
                            "Phone: " + visitor.getPhone() + "\n" +
                            "Purpose: " + visitor.getPurpose() + "\n\n" +
                            "Thank you.\nSmart Visitor Management System"
            );
            mailSender.send(message);
        } catch (Exception e) {
            System.err.println("Failed to send email: " + e.getMessage());
        }

        return savedLog;
    }

    @Override
    public VisitLog checkOut(Long visitId) {
        VisitLog log = visitLogRepository.findById(visitId)
                .orElseThrow(() -> new RuntimeException("Visit log not found"));

        log.setCheckOutTime(LocalDateTime.now());
        log.setStatus("Checked-Out");
        return visitLogRepository.save(log);
    }

    @Override
    public List<VisitLogDTO> getAllVisitLogs() {
        List<VisitLog> logs = visitLogRepository.findAll();

        return logs.stream().map(log -> {
            VisitLogDTO dto = new VisitLogDTO();
            dto.setVisitId(log.getVisitId());
            dto.setCheckInTime(log.getCheckInTime() != null ? log.getCheckInTime().toString() : null);
            dto.setCheckOutTime(log.getCheckOutTime() != null ? log.getCheckOutTime().toString() : null);
            dto.setStatus(log.getStatus());
            dto.setVisitorName(log.getVisitor().getName());
            dto.setEmployeeName(log.getEmployee().getName());
            return dto;
        }).collect(Collectors.toList());
    }
}
