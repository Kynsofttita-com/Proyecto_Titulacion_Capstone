package com.kynsoft.auth.service;

import com.kynsoft.auth.dto.AuditLogResponse;
import com.kynsoft.auth.enums.AuditAction;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.util.UUID;

public interface AuditService {
    Page<AuditLogResponse> findAll(UUID userId, AuditAction action, LocalDateTime from, LocalDateTime to, Pageable pageable);
}
