package com.kynsoft.auth.dto;

import com.kynsoft.auth.enums.AuditAction;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AuditLogResponse {
    private Long id;
    private UUID userId;
    private String userEmail;
    private AuditAction action;
    private String ipAddress;
    private LocalDateTime createdAt;
    private Map<String, Object> details;
}
