package com.kynsoft.auth.service;

import com.kynsoft.auth.dto.AuditLogResponse;
import com.kynsoft.auth.entity.AuditLog;
import com.kynsoft.auth.enums.AuditAction;
import com.kynsoft.auth.repository.AuditLogRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@Slf4j
public class AuditServiceImpl implements AuditService {

    @Autowired
    private AuditLogRepository auditLogRepository;

    @Override
    @Transactional(readOnly = true)
    public Page<AuditLogResponse> findAll(UUID userId, AuditAction action, LocalDateTime from, LocalDateTime to, Pageable pageable) {
        return auditLogRepository.findAll(
            (root, query, cb) -> {
                var predicates = new java.util.ArrayList<>();
                if (userId != null) {
                    predicates.add(cb.equal(root.get("user").get("id"), userId));
                }
                if (action != null) {
                    predicates.add(cb.equal(root.get("action"), action));
                }
                if (from != null) {
                    predicates.add(cb.greaterThanOrEqualTo(root.get("createdAt"), from));
                }
                if (to != null) {
                    predicates.add(cb.lessThanOrEqualTo(root.get("createdAt"), to));
                }
                return predicates.isEmpty() ? cb.conjunction() : cb.and(predicates.toArray(new jakarta.persistence.criteria.Predicate[0]));
            },
            pageable
        ).map(this::buildAuditResponse);
    }

    private AuditLogResponse buildAuditResponse(AuditLog log) {
        return AuditLogResponse.builder()
                .id(log.getId())
                .userId(log.getUser() != null ? log.getUser().getId() : null)
                .userEmail(log.getUser() != null ? log.getUser().getEmail() : null)
                .action(log.getAction())
                .ipAddress(log.getIpAddress())
                .createdAt(log.getCreatedAt())
                .details(log.getDetails())
                .build();
    }
}
