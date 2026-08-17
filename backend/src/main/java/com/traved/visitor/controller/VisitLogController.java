package com.traved.visitor.controller;

import com.traved.visitor.dto.VisitLogDTO;
import com.traved.visitor.model.VisitLog;
import com.traved.visitor.service.VisitLogService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/visits")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class VisitLogController {

    private final VisitLogService visitLogService;

    // Check-in visitor
    @PostMapping("/checkin")
    public VisitLog checkIn(@RequestParam Long visitorId, @RequestParam Long employeeId) {
        return visitLogService.checkIn(visitorId, employeeId);
    }

    // Check-out visitor
    @PostMapping("/checkout")
    public VisitLog checkOut(@RequestParam Long visitId) {
        return visitLogService.checkOut(visitId);
    }

    // List all visit logs (DTO-based response)
    @GetMapping
    public List<VisitLogDTO> getAllVisitLogs() {
        return visitLogService.getAllVisitLogs();
    }
}
