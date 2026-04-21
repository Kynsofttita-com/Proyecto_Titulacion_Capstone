package com.kynsoft.auth.controller;

import com.kynsoft.auth.dto.AuditLogResponse;
import com.kynsoft.auth.enums.AuditAction;
import com.kynsoft.auth.service.AuditService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.UUID;

@RestController
@RequestMapping("/api/audit")
@PreAuthorize("hasRole('ADMIN')")
@Tag(name = "Audit Logs", description = "Access to system audit logs")
@Slf4j
public class AuditController {

    @Autowired
    private AuditService auditService;

    @GetMapping
    @Operation(summary = "List audit logs", description = "Paginated audit logs with optional filters")
    @ApiResponse(responseCode = "200", description = "Audit logs retrieved successfully")
    public ResponseEntity<Page<AuditLogResponse>> findAll(
            @RequestParam(required = false) UUID userId,
            @RequestParam(required = false) AuditAction action,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime from,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime to,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("createdAt").descending());
        return ResponseEntity.ok(auditService.findAll(userId, action, from, to, pageable));
    }
}
