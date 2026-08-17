package com.traved.visitor.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class VisitLogDTO {
    private Long visitId;
    private String checkInTime;
    private String checkOutTime;
    private String status;
    private String visitorName;
    private String employeeName;
}
