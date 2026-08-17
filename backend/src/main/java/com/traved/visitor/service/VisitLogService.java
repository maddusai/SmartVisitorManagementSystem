package com.traved.visitor.service;

import com.traved.visitor.dto.VisitLogDTO;
import com.traved.visitor.model.VisitLog;

import java.util.List;

public interface VisitLogService {

    VisitLog checkIn(Long visitorId, Long employeeId);

    VisitLog checkOut(Long visitId);

    List<VisitLogDTO> getAllVisitLogs(); // ✅ update this return type
}
